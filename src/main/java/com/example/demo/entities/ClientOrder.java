package com.example.demo.entities;


import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
public class ClientOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "clientOrder")
    private List<OrderDetails> ordersDetail;

    @Basic
    private java.sql.Timestamp timeStamp;
    private boolean status;
    private String phoneNumber;
    private String country;
    private String city;
    private String postalCode;
    private String streetAddress;
    private String homeNumber;

    public ClientOrder() {
    }

    public ClientOrder(User user, Timestamp timeStamp, boolean status, String phoneNumber, String country, String city, String postalCode, String streetAddress, String homeNumber) {
        this.user = user;
        this.timeStamp = timeStamp;
        this.status = status;
        this.phoneNumber = phoneNumber;
        this.country = country;
        this.city = city;
        this.postalCode = postalCode;
        this.streetAddress = streetAddress;
        this.homeNumber = homeNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getHomeNumber() {
        return homeNumber;
    }

    public void setHomeNumber(String homeNumber) {
        this.homeNumber = homeNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", user=" + user +
                ", sqlTimestamp=" + timeStamp +
                ", status=" + status +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", streetAddress='" + streetAddress + '\'' +
                ", homeNumber='" + homeNumber + '\'' +
                '}';
    }
}
