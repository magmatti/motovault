package com.uken.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Entity
@Table(name="services")
public class Services {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    private long vehicleID;
    @Size(min = 3, max = 50, message = "Service type must be between 3 and 50 characters.")
    private String serviceType;
    private LocalDate date;
    private double total;
    @Email(message = "Email should be valid.")
    @Size(max = 100, message = "Email must not exceed 100 characters.")
    private String email;

    public Services(){}

    public Services(String serviceType, LocalDate date, double total, String email) {
        this.serviceType = serviceType;
        this.date = date;
        this.total = total;
        this.email = email;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public long getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(long vehicleID) {
        this.vehicleID = vehicleID;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getMail() {return email;}

    public void setMail(String mail) {this.email = mail;}
}
