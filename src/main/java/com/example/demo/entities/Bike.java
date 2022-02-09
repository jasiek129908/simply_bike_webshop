package com.example.demo.entities;

import org.hibernate.criterion.Order;
import java.util.List;
import javax.persistence.*;

@Entity
public class Bike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private BikeCategory bikeCategory;
    private String producer;
    private String model;
    private Float weight;
    private Float sizeOfFrame;
    private Float sizeOfWheel;
    private Float price;
    private int quantity;
    private String imagePath;

    @OneToMany(mappedBy = "bike",cascade = CascadeType.REMOVE)
    private List<OrderDetails> ordersDetail;

    public Bike() {

    }

    public Bike(BikeCategory bikeCategory, String producer, String model, Float weight, Float sizeOfFrame,
                Float sizeOfWheel, Float price, int quantity, String imagePath) {
        this.bikeCategory = bikeCategory;
        this.producer = producer;
        this.model = model;
        this.weight = weight;
        this.sizeOfFrame = sizeOfFrame;
        this.sizeOfWheel = sizeOfWheel;
        this.price = price;
        this.quantity = quantity;
        this.imagePath = imagePath;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BikeCategory getBikeCategory() {
        return bikeCategory;
    }

    public void setBikeCategory(BikeCategory bikeCategory) {
        this.bikeCategory = bikeCategory;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Float getSizeOfFrame() {
        return sizeOfFrame;
    }

    public void setSizeOfFrame(Float sizeOfFrame) {
        this.sizeOfFrame = sizeOfFrame;
    }

    public Float getSizeOfWheel() {
        return sizeOfWheel;
    }

    public void setSizeOfWheel(Float sizeOfWheel) {
        this.sizeOfWheel = sizeOfWheel;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<OrderDetails> getOrdersDetail() {
        return ordersDetail;
    }

    public void setOrdersDetail(List<OrderDetails> ordersDetail) {
        this.ordersDetail = ordersDetail;
    }
    @Override
    public String toString() {
        return "Bike{" +
                "id=" + id +
                ", bikeCategory=" + bikeCategory +
                ", producer='" + producer + '\'' +
                ", model='" + model + '\'' +
                ", weight=" + weight +
                ", sizeOfFrame=" + sizeOfFrame +
                ", sizeOfWheel=" + sizeOfWheel +
                ", price=" + price +
                ", quantity=" + quantity +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}
