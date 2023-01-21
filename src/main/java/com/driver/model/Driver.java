package com.driver.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Driver{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int driverId;

    private String mobile;

    private String password;


    //mapping with cab
    @OneToOne
    @JoinColumn
    private Cab cab;

    //mapping with tripBooking, driver is parent and tripBooking is child
    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL)
    List<TripBooking> tripBookingList = new ArrayList<>();


    public Driver() {
    }

    public Driver(int driverId, String mobile, String password) {
        this.driverId = driverId;
        this.mobile = mobile;
        this.password = password;
    }

    public Driver(String mobile, String password) {
        this.mobile = mobile;
        this.password = password;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}