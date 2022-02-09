package com.example.demo.service;

import com.example.demo.entities.Bike;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface BikeService {

    List<Bike> getLastThreeAddedBikes();
    List<Bike> getAllBikes();
    List<Bike> findBikesByPhrase(String text);
}
