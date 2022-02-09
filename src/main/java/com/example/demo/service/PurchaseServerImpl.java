package com.example.demo.service;

import com.example.demo.entities.*;
import com.example.demo.repository.BikeRepository;
import com.example.demo.repository.CartItemRepository;
import com.example.demo.repository.OrderDetailsRepository;
import com.example.demo.repository.ClientOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

@Service
public class PurchaseServerImpl implements PurchaseService {

    @Autowired
    private ClientOrderRepository clientOrderRepository;

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private BikeRepository bikeRepository;

    @Override
    public void processPurchase(ClientOrder clientOrder, User user) {
        clientOrder.setUser(user);
        clientOrder.setTimeStamp(new Timestamp(new Date().getTime()));
        clientOrder.setStatus(false);
        ClientOrder orderReference = clientOrderRepository.save(clientOrder);

        List<CartItem> userBikes = shoppingCartService.listCarItems(user);
        for (CartItem cartItem : userBikes) {
            OrderDetails orderDetails = new OrderDetails();
            orderDetails.setOrder(orderReference);
            orderDetails.setBike(cartItem.getBike());
            orderDetails.setQuantity(cartItem.getQuantity());
            orderDetails.setPrice((double) cartItem.getQuantity() * cartItem.getBike().getPrice());
            orderDetailsRepository.save(orderDetails);

            //cleaning
            cartItemRepository.delete(cartItem);
            Bike bike = cartItem.getBike();
            bike.setQuantity(bike.getQuantity() - orderDetails.getQuantity());
            bikeRepository.save(bike);
        }
    }

    @Override
    public String calculateTotalPriceByOrder(ClientOrder order) {
        List<OrderDetails> list = orderDetailsRepository.findByClientOrder(order);
        Double sum = 0d;
        for (OrderDetails item : list) {
            sum += item.getPrice();
        }
        DecimalFormat df = new DecimalFormat(".00");
        return df.format(sum);
    }

}
