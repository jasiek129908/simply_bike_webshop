package com.example.demo.entities;

import javax.persistence.*;

@Entity
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_order_id")
    private ClientOrder clientOrder;

    @ManyToOne
    @JoinColumn(name = "bike_id")
    private Bike bike;


    private Double price;
    private Integer quantity;


    public OrderDetails() {
    }

    public OrderDetails(ClientOrder clientOrder, Bike bike, Double price, Integer quantity) {
        this.clientOrder = clientOrder;
        this.bike = bike;
        this.price = price;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClientOrder getOrder() {
        return clientOrder;
    }

    public void setOrder(ClientOrder clientOrder) {
        this.clientOrder = clientOrder;
    }

    public Bike getBike() {
        return bike;
    }

    public void setBike(Bike bike) {
        this.bike = bike;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public ClientOrder getClientOrder() {
        return clientOrder;
    }

    public void setClientOrder(ClientOrder clientOrder) {
        this.clientOrder = clientOrder;
    }

    @Override
    public String toString() {
        return "OrderDetails{" +
                "id=" + id +
                ", order=" + clientOrder +
                ", bike=" + bike +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
