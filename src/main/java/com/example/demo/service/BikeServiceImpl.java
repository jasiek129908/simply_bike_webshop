package com.example.demo.service;

import com.example.demo.entities.Bike;
import com.example.demo.repository.BikeRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BikeServiceImpl implements BikeService {

    @Autowired
    private BikeRepository bikeRepository;

    @Override
    public List<Bike> getLastThreeAddedBikes() {
        return bikeRepository.findTop3BikesByOrderByIdDesc();
    }

    @Override
    public List<Bike> getAllBikes() {
        return bikeRepository.findByQuantityGreaterThan(0);
    }

    @Override
    public List<Bike> findBikesByPhrase(String text) {
        List<Bike> foundBikes = bikeRepository.findPhrase(text);
        if (foundBikes == null) {
            foundBikes = bikeRepository.findAll();
        }
        return foundBikes;
    }


}
