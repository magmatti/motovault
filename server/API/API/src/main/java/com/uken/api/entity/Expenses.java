package com.uken.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Entity
@Table(name="expenses")
public class Expenses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    private long VehicleID;
    @Size(min = 3, max = 50, message = "Expenses type must be between 3 and 50 characters.")
    private String expensesType;
    private LocalDate date;
    private double total;
    @Email(message = "Email should be valid.")
    @Size(max = 100, message = "Email must not exceed 100 characters.")
    private String email;

    public Expenses(){};

    public Expenses(String expensesType, LocalDate date, Double total, String email){
        this.expensesType = expensesType;
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
        return VehicleID;
    }

    public void setVehicleID(long vehicleID) {
        VehicleID = vehicleID;
    }

    public String getExpensesType() {
        return expensesType;
    }

    public void setExpensesType(String expensesType) {
        this.expensesType = expensesType;
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
