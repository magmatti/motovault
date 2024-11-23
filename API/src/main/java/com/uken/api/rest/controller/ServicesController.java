package com.uken.api.rest.controller;

import com.uken.api.entity.Services;
import com.uken.api.rest.service.ServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/services")
public class ServicesController {

    @Autowired
    private ServicesService servicesService;

    @PostMapping
    public ResponseEntity<Services> createService(@RequestBody Services services) {
        Services savedService = servicesService.saveServices(services);
        return new ResponseEntity<>(savedService, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Services>> getAllServices() {
        List<Services> servicesList = servicesService.getAllServices();
        return new ResponseEntity<>(servicesList, HttpStatus.OK);
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
}

