package com.uken.api.rest.service;

import com.uken.api.entity.Services;
import com.uken.api.rest.repostitory.ServicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicesService {

    @Autowired
    private ServicesRepository servicesRepository;

    public Services saveServices(Services services) {
        return servicesRepository.save(services);
    }

    public List<Services> getAllServices() {
        return servicesRepository.findAll();
    }

    public Optional<Services> getServiceById(long id) {
        return servicesRepository.findById(id);
    }

     public void deleteServiceById(long id) {
         servicesRepository.deleteById(id);
     }
}
