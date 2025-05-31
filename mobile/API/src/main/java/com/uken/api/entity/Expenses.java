package com.uken.api.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="expenses")
public class Expenses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    private long VehicleID;
    private String expensesType;
    private LocalDate date;
    private double total;

    public Expenses(){};

    public Expenses(String expensesType, LocalDate date, Double total){
        this.expensesType = expensesType;
        this.date = date;
        this.total = total;
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
}
