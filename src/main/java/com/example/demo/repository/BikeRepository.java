package com.example.demo.repository;

import com.example.demo.entities.Bike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BikeRepository extends JpaRepository<Bike, Long> {

    List<Bike> findTop3BikesByOrderByIdDesc();

    List<Bike> findByQuantityGreaterThan(int quantity);

    Bike findBikeByProducerAndModel(String producer, String model);

    @Query("SELECT b FROM Bike b WHERE UPPER(b.producer) LIKE UPPER(CONCAT('%',:phrase,'%')) " +
            "OR UPPER(b.model) LIKE UPPER(CONCAT('%',:phrase,'%'))")
    List<Bike> findPhrase(@Param("phrase") String phrase);

    @Modifying
    @Query("update Bike b set b.quantity = :newQuantity WHERE b.id = :bikeId")
    void updateBikeQuantity(@Param("bikeId") Long id, @Param("newQuantity") int quantity);

}
