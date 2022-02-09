package com.example.demo.service;

import com.example.demo.entities.ClientOrder;
import com.example.demo.entities.OrderDetails;
import com.example.demo.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClientOrderService {

    List<ClientOrder> getAllOrders();
    List<ClientOrder> getAllUserOrder(User user);
    List<OrderDetails> getOrderDetailsByOrderId(Long clientOrderId);
    User getUserByOrderId(Long orderId);
}
