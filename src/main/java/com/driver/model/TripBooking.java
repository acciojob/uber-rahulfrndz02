package com.driver.model;

import org.springframework.beans.propertyeditors.CurrencyEditor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class TripBooking{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int tripBookingId;

    private String fromLocation;

    private String toLocation;

    private int distanceInKm;

    private TripStatus status;

    private int bill;

    //mapping with driver
    @ManyToOne
    @JoinColumn
    private Driver driver;

    //mapping with customer
    @ManyToOne
    @JoinColumn
    private Customer customer;

    public TripBooking() {
    }

    public TripBooking(int tripBookingId, String fromLocation, String toLocation, int distanceInKm, TripStatus status, int bill) {
        this.tripBookingId = tripBookingId;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.distanceInKm = distanceInKm;
        this.status = status;
        this.bill = bill;
    }

    public TripBooking(String fromLocation, String toLocation, int distanceInKm, TripStatus status) {
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.distanceInKm = distanceInKm;
        this.status = status;
    }

    public TripBooking(int tripBookingId, String fromLocation, String toLocation, int distanceInKm) {
        this.tripBookingId = tripBookingId;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.distanceInKm = distanceInKm;
    }

    public int getTripBookingId() {
        return tripBookingId;
    }

    public void setTripBookingId(int tripBookingId) {
        this.tripBookingId = tripBookingId;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public int getDistanceInKm() {
        return distanceInKm;
    }

    public void setDistanceInKm(int distanceInKm) {
        this.distanceInKm = distanceInKm;
    }

    public TripStatus getStatus() {
        return status;
    }

    public void setStatus(TripStatus status) {
        this.status = status;
    }

    public int getBill() {
        return bill;
    }

    public void setBill(int bill) {
        this.bill = bill;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}