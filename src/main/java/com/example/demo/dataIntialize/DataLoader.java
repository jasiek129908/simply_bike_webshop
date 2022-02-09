package com.example.demo.dataIntialize;

import com.example.demo.entities.Bike;
import com.example.demo.entities.BikeCategory;
import com.example.demo.entities.Role;
import com.example.demo.entities.User;
import com.example.demo.repository.BikeRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    private BikeRepository bikeRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public void run(ApplicationArguments args) throws IOException {
        bikeRepository.save(new Bike(BikeCategory.CITY, "Romet", "1.2 Pro", 7.7f,
                19f, 30f, 1500.20f, 2, "/images/romet1.jpg"));
        bikeRepository.save(new Bike(BikeCategory.CITY, "Romet", "R2", 9f,
                20f, 30f, 2834f, 1, "/images/romet2.jpg"));
        bikeRepository.save(new Bike(BikeCategory.MTB, "INDIANA", "X-Enduro 2.7", 10f,
                18f, 33f, 3309.99f, 1, "/images/indiana.jpg"));
        bikeRepository.save(new Bike(BikeCategory.ROAD, "TRIBAN", "RC 100", 6f,
                19f, 28f, 1200.99f, 1, "/images/triban.jpg"));
        bikeRepository.save(new Bike(BikeCategory.ROAD, "Kross", "Vento", 6.32f,
                19f, 28f, 4567.42f, 1, "/images/krossvento.jpg"));


        roleRepository.save(new Role("USER"));
        roleRepository.save(new Role("ADMIN"));

        userRepository.save(new User("jan", "asddsa", "jan@wp.pl",
                bCryptPasswordEncoder.encode("haslo"), roleRepository.getRoleByName("USER")));
        userRepository.save(new User("admin", "admin", "admin",
                bCryptPasswordEncoder.encode("admin"), roleRepository.getRoleByName("ADMIN")));
    }
}
