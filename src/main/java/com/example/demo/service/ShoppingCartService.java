package com.example.demo.service;

import com.example.demo.entities.Bike;
import com.example.demo.entities.CartItem;
import com.example.demo.entities.ClientOrder;
import com.example.demo.entities.User;
import com.example.demo.repository.BikeRepository;
import com.example.demo.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.*;

@Service
public class ShoppingCartService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private BikeRepository bikeRepository;

    public List<CartItem> listCarItems(User user) {
        return cartItemRepository.findByUserOrderById(user);
    }

    public Integer addProduct(Long bikeId, Integer quantity, User user) {
        Bike bike = bikeRepository.findById(bikeId).get();
        CartItem cartItem = cartItemRepository.findByUserAndBike(user, bike);

        int addedQuantity = quantity;
        if (cartItem != null) {
            if (addedQuantity + 1 <= bike.getQuantity()) {
                addedQuantity = cartItem.getQuantity() + quantity;
                cartItem.setQuantity(addedQuantity);
            }
        } else {
            cartItem = new CartItem();
            cartItem.setQuantity(addedQuantity);
            cartItem.setBike(bike);
            cartItem.setUser(user);
        }
        cartItemRepository.save(cartItem);
        return addedQuantity;
    }

    @Transactional
    public String updatedQuantity(Integer quantity, Long bikeId, User user) {
        Bike bike = bikeRepository.findById(bikeId).get();
        if (quantity == 0) {
            removeBike(bikeId, user);
        }
        else if (bike.getQuantity() >= quantity) {
            cartItemRepository.updateQuantity(quantity, bikeId, user.getId());
        }
        return String.valueOf(quantity);
    }

    @Transactional
    public void removeBike(Long bikeId, User user) {
        cartItemRepository.deleteByUserAndBike(user.getId(), bikeId);
    }

    public String calculateTotalPrice(List<CartItem> list) {
        Double sum = 0d;
        for (CartItem item : list) {
            sum += item.getBike().getPrice() * item.getQuantity();
        }
        DecimalFormat df = new DecimalFormat(".00");
        return df.format(sum);
    }
}
