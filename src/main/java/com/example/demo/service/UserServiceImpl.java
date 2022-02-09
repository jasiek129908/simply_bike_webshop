package com.example.demo.service;

import com.example.demo.entities.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;


    @Override
    public void registerUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User getCurrentlyLoggedUser(Authentication authentication) {
        if(authentication == null)
            return null;

        User user = null;
        Object principal = authentication.getPrincipal();
        if(principal instanceof CustomUserDetails){
            user = ((CustomUserDetails) principal).getUser();
        }
        return user;
    }

}
