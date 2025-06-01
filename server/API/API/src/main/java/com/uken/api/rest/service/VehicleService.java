package com.uken.api.rest.service;

import com.uken.api.entity.Vehicle;
import com.uken.api.rest.repostitory.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    public Vehicle saveVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }
    public List<Vehicle> getVehiclesByEmail(String email) {
        return vehicleRepository.findAllByEmail(email);
    }

    public Optional<Vehicle> getVehicleByIdAndEmail(long ID, String email) {
        return vehicleRepository.findByIDAndEmail(ID, email);
    }

    public void deleteVehicleById(long id) {
        vehicleRepository.deleteById(id);
    }
}
