package com.example.demo.service;

import com.example.demo.entities.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    void registerUser(User user);

    User getCurrentlyLoggedUser(Authentication authentication);
}
