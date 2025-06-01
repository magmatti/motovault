package com.uken.api.rest.controller;

import com.uken.api.entity.Services;
import com.uken.api.rest.service.ServicesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/services")
public class ServicesController {

    @Autowired
    private ServicesService servicesService;

    @PostMapping
    public ResponseEntity<Services> createService(@Valid @RequestBody Services services) {
        Services savedService = servicesService.saveServices(services);
        return new ResponseEntity<>(savedService, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Services>> getAllServices() {
        List<Services> servicesList = servicesService.getAllServices();
        return new ResponseEntity<>(servicesList, HttpStatus.OK);
    }

    @GetMapping("/getAll/{email}")
    public ResponseEntity<List<Services>> getVehiclesByEmail(@PathVariable String email) {
        List<Services> services = servicesService.getServicesByMail(email);
        if (services.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(services, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Services> getServiceById(@PathVariable long id) {
        Optional<Services> service = servicesService.getServiceById(id);
        return service.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable long id) {
        servicesService.deleteServiceById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/yearly/{year}/{email}")
    public ResponseEntity<?> getYearlyServicesByTypeAndEmail(@PathVariable int year, @PathVariable String email) {
        List<Object[]> yearlyServices = servicesService.getYearlyServicesByTypeAndEmail(year, email);

        List<Map<String, Object>> response = yearlyServices.stream()
                .map(service -> Map.of(
                        "category", service[0],
                        "value", service[1]
                ))
                .toList();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/monthly/{year}/{month}/{email}")
    public ResponseEntity<?> getMonthlyServicesByTypeAndEmail(@PathVariable int year, @PathVariable int month, @PathVariable String email) {
        List<Object[]> monthlyServices = servicesService.getMonthlyServicesByTypeAndEmail(year, month, email);

        List<Map<String, Object>> response = monthlyServices.stream()
                .map(service -> Map.of(
                        "category", service[0],
                        "value", service[1]
                ))
                .toList();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Services> updateService(
            @PathVariable long id,
            @Valid @RequestBody Services updatedService) {
        Optional<Services> existingService = servicesService.getServiceById(id);
        if (existingService.isPresent()) {
            Services savedService = servicesService.updateService(id, updatedService);
            return new ResponseEntity<>(savedService, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

