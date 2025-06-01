package com.uken.api.rest.controller;

import com.uken.api.APIConnector;
import com.uken.api.entity.Vehicle;
import com.uken.api.entity.VehicleRequest;
import com.uken.api.rest.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @PostMapping
    public ResponseEntity<Vehicle> createVehicle(@RequestBody VehicleRequest vehicleRequest) {
        APIConnector apiConnector = new APIConnector();
        Vehicle vehicle = apiConnector.getInfo(vehicleRequest.getVin(), vehicleRequest.getCarName(), vehicleRequest.getMail());
        vehicleService.saveVehicle(vehicle);
        return new ResponseEntity<>(vehicle, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Vehicle>> getAllVehicles() {
        List<Vehicle> vehicleList = vehicleService.getAllVehicles();
        return new ResponseEntity<>(vehicleList, HttpStatus.OK);
    }

    @GetMapping("/getAll/{email}")
    public ResponseEntity<List<Vehicle>> getVehiclesByEmail(@PathVariable String email) {
        List<Vehicle> vehicles = vehicleService.getVehiclesByEmail(email);
        if (vehicles.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(vehicles, HttpStatus.OK);
    }

    @GetMapping("/{id}/{email}")
    public ResponseEntity<Vehicle> getVehicleByIdAndEmail(@PathVariable long id, @PathVariable String email) {
        Optional<Vehicle> vehicle = vehicleService.getVehicleByIdAndEmail(id, email);
        return vehicle.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable long id) {
        vehicleService.deleteVehicleById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
