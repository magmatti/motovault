package org.example;

public class Main {
    public static void main(String[] args) {
        APIConnecter APIConnecter = new APIConnecter();
        Vehicle vehicle = APIConnecter.getInfo("VF1BG0N0526997886");
        System.out.println(vehicle.getModel());
        System.out.println(vehicle.getEnginePosition());
    }
}
