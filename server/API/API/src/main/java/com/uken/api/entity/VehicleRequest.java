package com.uken.api.entity;

public class VehicleRequest {
    private String vin;
    private String carName;
    private String mail;

    public String getVin() { return vin; }
    public void setVin(String vin) { this.vin = vin; }
    public String getCarName() { return carName; }
    public void setCarName(String carName) { this.carName = carName; }
    public String getMail() { return mail; }
    public void setMail(String mail) { this.mail = mail; }
}
