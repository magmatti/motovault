package com.uken.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import org.json.JSONArray;
import org.json.JSONObject;

@Entity
@Table(name="vehicles")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    private int userID;
    private String vin;
    @Size(min = 3, max = 50, message = "Car name must be between 3 and 50 characters.")
    private String carName;
    @Email(message = "Email should be valid.")
    @Size(max = 100, message = "Email must not exceed 100 characters.")
    private String email;
    @Transient
    private int vehicleId;
    @Transient
    private String make;
    @Transient
    private String model;
    @Transient
    private int modelYear;
    @Transient
    private String productType;
    @Transient
    private String body;
    @Transient
    private String drive;
    @Transient
    private int engineDisplacement;
    @Transient
    private int enginePowerKw;
    @Transient
    private int enginePowerHp;
    @Transient
    private String fuelType;
    @Transient
    private String engineCode;
    @Transient
    private String transmission;
    @Transient
    private int numberOfGears;
    @Transient
    private String manufacturer;
    @Transient
    private String manufacturerAddress;
    @Transient
    private String plantCountry;
    @Transient
    private double engineCylinderBore;
    @Transient
    private int engineCylinders;
    @Transient
    private String engineCylindersPosition;
    @Transient
    private int engineOilCapacity;
    @Transient
    private String enginePosition;
    @Transient
    private int engineRpm;
    @Transient
    private int engineStroke;
    @Transient
    private String engineTurbine;
    @Transient
    private String valveTrain;
    @Transient
    private double fuelConsumptionCombined;
    @Transient
    private double fuelConsumptionExtraUrban;
    @Transient
    private double fuelConsumptionUrban;
    @Transient
    private double averageCO2Emission;
    @Transient
    private int valvesPerCylinder;
    @Transient
    private int numberOfAxles;
    @Transient
    private int numberOfDoors;
    @Transient
    private String numberOfSeats;
    @Transient
    private String powerSteering;
    @Transient
    private String frontBrakes;
    @Transient
    private String rearBrakes;
    @Transient
    private String steeringType;
    @Transient
    private String frontSuspension;
    @Transient
    private String wheelSize;
    @Transient
    private int wheelbase;
    @Transient
    private int height;
    @Transient
    private int length;
    @Transient
    private int width;
    @Transient
    private int trackRear;
    @Transient
    private int maxSpeed;
    @Transient
    private int weightEmpty;
    @Transient
    private int maxWeight;
    @Transient
    private int maxRoofLoad;
    @Transient
    private int permittedTrailerLoadWithoutBrakes;
    @Transient
    private boolean abs;
    @Transient
    private String checkDigit;
    @Transient
    private String sequentialNumber;

    public Vehicle(){}
    public Vehicle(String response, String carName, String email) {
        this.carName = carName;
        this.email = email;
        JSONObject jsonResponse = new JSONObject(response);
        JSONArray decodeArray = jsonResponse.getJSONArray("decode");

        for (int i = 0; i < decodeArray.length(); i++) {
            JSONObject item = decodeArray.getJSONObject(i);
            String label = item.getString("label");
            Object value = item.get("value");

            switch (label) {
                case "VIN":
                    this.vin = (String) value;
                    break;
                case "Vehicle ID":
                    this.vehicleId = (int) value;
                    break;
                case "Make":
                    this.make = (String) value;
                    break;
                case "Model":
                    this.model = (String) value;
                    break;
                case "Model Year":
                    this.modelYear = (int) value;
                    break;
                case "Product Type":
                    this.productType = (String) value;
                    break;
                case "Body":
                    this.body = (String) value;
                    break;
                case "Drive":
                    this.drive = (String) value;
                    break;
                case "Engine Displacement (ccm)":
                    this.engineDisplacement = (int) value;
                    break;
                case "Engine Power (kW)":
                    this.enginePowerKw = (int) value;
                    break;
                case "Engine Power (HP)":
                    this.enginePowerHp = (int) value;
                    break;
                case "Fuel Type - Primary":
                    this.fuelType = (String) value;
                    break;
                case "Engine Code":
                    this.engineCode = (String) value;
                    break;
                case "Transmission":
                    this.transmission = (String) value;
                    break;
                case "Number of Gears":
                    this.numberOfGears = (int) value;
                    break;
                case "Manufacturer":
                    this.manufacturer = (String) value;
                    break;
                case "Manufacturer Address":
                    this.manufacturerAddress = (String) value;
                    break;
                case "Plant Country":
                    this.plantCountry = (String) value;
                    break;
                case "Engine Cylinder Bore (mm)":
                    this.engineCylinderBore = ((Number) value).doubleValue();
                    break;
                case "Engine Cylinders":
                    this.engineCylinders = (int) value;
                    break;
                case "Engine Cylinders Position":
                    this.engineCylindersPosition = (String) value;
                    break;
                case "Engine Oil Capacity (l)":
                    this.engineOilCapacity = (int) value;
                    break;
                case "Engine Position":
                    this.enginePosition = (String) value;
                    break;
                case "Engine RPM":
                    this.engineRpm = (int) value;
                    break;
                case "Engine Stroke (mm)":
                    this.engineStroke = (int) value;
                    break;
                case "Engine Turbine":
                    this.engineTurbine = (String) value;
                    break;
                case "Valve Train":
                    this.valveTrain = (String) value;
                    break;
                case "Fuel Consumption Combined (l/100km)":
                    this.fuelConsumptionCombined = ((Number) value).doubleValue();
                    break;
                case "Fuel Consumption Extra Urban (l/100km)":
                    this.fuelConsumptionExtraUrban = ((Number) value).doubleValue();
                    break;
                case "Fuel Consumption Urban (l/100km)":
                    this.fuelConsumptionUrban = ((Number) value).doubleValue();
                    break;
                case "Average CO2 Emission (g/km)":
                    this.averageCO2Emission = ((Number) value).doubleValue();
                    break;
                case "Valves per Cylinder":
                    this.valvesPerCylinder = (int) value;
                    break;
                case "Number of Axles":
                    this.numberOfAxles = (int) value;
                    break;
                case "Number of Doors":
                    this.numberOfDoors = (int) value;
                    break;
                case "Number of Seats":
                    this.numberOfSeats = (String) value;
                    break;
                case "Power Steering":
                    this.powerSteering = (String) value;
                    break;
                case "Front Brakes":
                    this.frontBrakes = (String) value;
                    break;
                case "Rear Brakes":
                    this.rearBrakes = (String) value;
                    break;
                case "Steering Type":
                    this.steeringType = (String) value;
                    break;
                case "Front Suspension":
                    this.frontSuspension = (String) value;
                    break;
                case "Wheel Size":
                    this.wheelSize = (String) value;
                    break;
                case "Wheelbase (mm)":
                    this.wheelbase = (int) value;
                    break;
                case "Height (mm)":
                    this.height = (int) value;
                    break;
                case "Length (mm)":
                    this.length = (int) value;
                    break;
                case "Width (mm)":
                    this.width = (int) value;
                    break;
                case "Track Rear (mm)":
                    this.trackRear = (int) value;
                    break;
                case "Max Speed (km/h)":
                    this.maxSpeed = (int) value;
                    break;
                case "Weight Empty (kg)":
                    this.weightEmpty = (int) value;
                    break;
                case "Max Weight (kg)":
                    this.maxWeight = (int) value;
                    break;
                case "Max roof load (kg)":
                    this.maxRoofLoad = (int) value;
                    break;
                case "Permitted trailer load without brakes (kg)":
                    this.permittedTrailerLoadWithoutBrakes = (int) value;
                    break;
                case "ABS":
                    this.abs = (int) value == 1;
                    break;
                case "Check Digit":
                    this.checkDigit = (String) value;
                    break;
                case "Sequential Number":
                    this.sequentialNumber = (String) value;
                    break;
            }
        }
    }

    public int getID() {return ID;}
    public void setID(int ID) {this.ID = ID;}
    public String getVin() { return vin; }
    public int getVehicleId() { return vehicleId; }
    public String getMake() { return make; }
    public String getModel() { return model; }
    public int getModelYear() { return modelYear; }
    public String getProductType() { return productType; }
    public String getBody() { return body; }
    public String getDrive() { return drive; }
    public int getEngineDisplacement() { return engineDisplacement; }
    public int getEnginePowerKw() { return enginePowerKw; }
    public int getEnginePowerHp() { return enginePowerHp; }
    public String getFuelType() { return fuelType; }
    public String getEngineCode() { return engineCode; }
    public String getTransmission() { return transmission; }
    public int getNumberOfGears() { return numberOfGears; }
    public String getManufacturer() { return manufacturer; }
    public String getManufacturerAddress() { return manufacturerAddress; }
    public String getPlantCountry() { return plantCountry; }
    public double getEngineCylinderBore() { return engineCylinderBore; }
    public int getEngineCylinders() { return engineCylinders; }
    public String getEngineCylindersPosition() { return engineCylindersPosition; }
    public int getEngineOilCapacity() { return engineOilCapacity; }
    public String getEnginePosition() { return enginePosition; }
    public int getEngineRpm() { return engineRpm; }
    public int getEngineStroke() { return engineStroke; }
    public String getEngineTurbine() { return engineTurbine; }
    public String getValveTrain() { return valveTrain; }
    public double getFuelConsumptionCombined() { return fuelConsumptionCombined; }
    public double getFuelConsumptionExtraUrban() { return fuelConsumptionExtraUrban; }
    public double getFuelConsumptionUrban() { return fuelConsumptionUrban; }
    public double getAverageCO2Emission() { return averageCO2Emission; }
    public int getValvesPerCylinder() { return valvesPerCylinder; }
    public int getNumberOfAxles() { return numberOfAxles; }
    public int getNumberOfDoors() { return numberOfDoors; }
    public String getNumberOfSeats() { return numberOfSeats; }
    public String getPowerSteering() { return powerSteering; }
    public String getFrontBrakes() { return frontBrakes; }
    public String getRearBrakes() { return rearBrakes; }
    public String getSteeringType() { return steeringType; }
    public String getFrontSuspension() { return frontSuspension; }
    public String getWheelSize() { return wheelSize; }
    public int getWheelbase() { return wheelbase; }
    public int getHeight() { return height; }
    public int getLength() { return length; }
    public int getWidth() { return width; }
    public int getTrackRear() { return trackRear; }
    public int getMaxSpeed() { return maxSpeed; }
    public int getWeightEmpty() { return weightEmpty; }
    public int getMaxWeight() { return maxWeight; }
    public int getMaxRoofLoad() { return maxRoofLoad; }
    public int getPermittedTrailerLoadWithoutBrakes() { return permittedTrailerLoadWithoutBrakes; }
    public boolean isAbs() { return abs; }
    public String getCheckDigit() { return checkDigit; }
    public String getSequentialNumber() { return sequentialNumber; }
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
}
