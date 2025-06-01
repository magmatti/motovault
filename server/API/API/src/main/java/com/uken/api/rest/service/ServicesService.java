package com.uken.api.rest.service;

import com.uken.api.entity.Expenses;
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

    public List<Services> getServicesByMail(String email) {return servicesRepository.findAllByEmail(email);}

    public List<Object[]> getYearlyServicesByTypeAndEmail(int year, String email) {
        return servicesRepository.findYearlyServicesByTypeAndEmail(year, email);
    }

    public List<Object[]> getMonthlyServicesByTypeAndEmail(int year, int month, String email) {
        return servicesRepository.findMonthlyServicesByTypeAndEmail(year, month, email);
    }

    public Services updateService(long id, Services updatedService) {
        return servicesRepository.findById(id).map(service -> {
            service.setServiceType(updatedService.getServiceType());
            service.setDate(updatedService.getDate());
            service.setTotal(updatedService.getTotal());
            service.setMail(updatedService.getMail());
            return servicesRepository.save(service);
        }).orElseThrow(() -> new IllegalArgumentException("Service with ID " + id + " not found"));
    }
}
