package com.example.demo.service;

import com.example.demo.entities.Bike;
import com.example.demo.entities.ClientOrder;
import com.example.demo.entities.OrderDetails;
import com.example.demo.entities.User;
import com.example.demo.repository.BikeRepository;
import com.example.demo.repository.ClientOrderRepository;
import com.example.demo.repository.OrderDetailsRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BikeRepository bikeRepository;
    @Autowired
    private ClientOrderRepository clientOrderRepository;


    @Override
    public List<User> getAllUsers() {
        List<User> userList = userRepository.findAll();
        Iterator<User> i = userList.iterator();
        while (i.hasNext()) {
            User s = i.next();
            if (s.getRole().getName().equalsIgnoreCase("ADMIN") || s.isStatusDeleted() == true)
                i.remove();
        }
        return userList;
    }

    @Override
    public void setUserStatusToDeleted(Long userId) {
        User userToDelete = userRepository.findById(userId).get();
        userToDelete.setStatusDeleted(true);
        userRepository.save(userToDelete);
    }

    @Override
    public boolean updateBikeData(Bike bike) {
        Bike bikeToUpdate = bikeRepository.findById(bike.getId()).get();
        if (bike.getQuantity() < 0 || bike.getPrice() <= 0 || bike.getSizeOfFrame() <= 0
                || bike.getSizeOfWheel() <= 0 || bike.getWeight() <= 0)
            return false;
        bikeToUpdate.setQuantity(bike.getQuantity());
        bikeToUpdate.setModel(bike.getModel());
        bikeToUpdate.setPrice(bike.getPrice());
        bikeToUpdate.setProducer(bike.getProducer());
        bikeToUpdate.setSizeOfFrame(bike.getSizeOfFrame());
        bikeToUpdate.setSizeOfWheel(bike.getSizeOfWheel());
        bikeToUpdate.setWeight(bike.getWeight());
        bikeRepository.save(bikeToUpdate);
        return true;
    }

    @Override
    public void addBike(Bike bike) {
        bikeRepository.save(bike);
    }

    @Override
    public void confirmOrder(Long orderId) {
        ClientOrder clientOrder = clientOrderRepository.findById(orderId).get();
        clientOrder.setStatus(true);
        clientOrderRepository.save(clientOrder);
    }
}
