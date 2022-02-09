package com.example.demo.service;

import com.example.demo.entities.ClientOrder;
import com.example.demo.entities.OrderDetails;
import com.example.demo.entities.User;
import com.example.demo.repository.ClientOrderRepository;
import com.example.demo.repository.OrderDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientOrderServiceImpl implements ClientOrderService {

    @Autowired
    private ClientOrderRepository clientOrderRepository;
    @Autowired
    private OrderDetailsRepository orderDetailsRepository;


    @Override
    public List<ClientOrder> getAllOrders() {
        return clientOrderRepository.findAll();
    }

    @Override
    public List<ClientOrder> getAllUserOrder(User user) {
        return clientOrderRepository.findOrdersByUser(user);
    }

    @Override
    public List<OrderDetails> getOrderDetailsByOrderId(Long clientOrderId) {
        return orderDetailsRepository.findByClientOrderId(clientOrderId);
    }

    @Override
    public User getUserByOrderId(Long orderId) {
        return clientOrderRepository.findById(orderId).get().getUser();
    }

}
