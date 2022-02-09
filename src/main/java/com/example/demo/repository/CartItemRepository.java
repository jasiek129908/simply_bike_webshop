package com.example.demo.repository;

import com.example.demo.entities.Bike;
import com.example.demo.entities.CartItem;
import com.example.demo.entities.ClientOrder;
import com.example.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    public List<CartItem> findByUserOrderById(User user);
    public CartItem findByUserAndBike(User user, Bike bike);

    @Modifying
    @Query("UPDATE CartItem u Set u.quantity = ?1 WHERE u.bike.id = ?2 "
            + "AND u.user.id = ?3")
    public void updateQuantity(Integer quantity, Long bikeId, Long userId);

    @Modifying
    @Query("DELETE FROM CartItem u WHERE u.user.id = ?1 AND u.bike.id = ?2")
    public void deleteByUserAndBike(Long userId, Long bikeId);
}
