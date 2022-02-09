package com.example.demo.service;

import com.example.demo.entities.ClientOrder;
import com.example.demo.entities.User;
import org.springframework.stereotype.Service;

@Service
public interface PurchaseService {
    void processPurchase(ClientOrder clientOrder, User user);
    String calculateTotalPriceByOrder(ClientOrder order);
}
