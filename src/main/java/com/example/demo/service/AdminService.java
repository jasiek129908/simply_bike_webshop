package com.example.demo.service;

import com.example.demo.entities.Bike;
import com.example.demo.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdminService {

    List<User> getAllUsers();
    void setUserStatusToDeleted(Long userId);
    boolean updateBikeData(Bike bike);
    void addBike(Bike bike);
    void confirmOrder(Long orderId);

}
