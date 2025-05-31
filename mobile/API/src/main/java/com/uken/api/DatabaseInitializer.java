//package com.uken.api;
//
//import com.uken.api.entity.Expenses;
//import com.uken.api.entity.Services;
//import com.uken.api.entity.User;
//import com.uken.api.entity.Vehicle;
//import com.uken.api.rest.service.ExpensesService;
//import com.uken.api.rest.service.ServicesService;
//import com.uken.api.rest.service.UserService;
//import com.uken.api.rest.service.VehicleService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDate;
//
//@Component
//public class DatabaseInitializer implements CommandLineRunner {
//
//    private final VehicleService vehicleService;
//    private final ExpensesService expensesService;
//    private final ServicesService servicesService;
//    private final UserService userService;
//
//    @Autowired
//    public DatabaseInitializer(VehicleService vehicleService, ExpensesService expensesService, ServicesService servicesService, UserService userService) {
//        this.vehicleService = vehicleService;
//        this.expensesService = expensesService;
//        this.servicesService = servicesService;
//        this.userService = userService;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        APIConnector apiConnector = new APIConnector();
//        Vehicle vehicle = apiConnector.getInfo("VF1BG0N0526997886");
//
//        vehicleService.saveVehicle(vehicle);
//        System.out.println("Vehicle zapisany do bazy.");
//
//        System.out.println(vehicleService.getVehicleById(1).get().getModel());
//
//        Expenses expenses = new Expenses("PALIWO", LocalDate.now(), 55.60);
//
//        expensesService.saveExpenses(expenses);
//        System.out.println("Expenses zapisane do bazy.");
//
//        System.out.println(expensesService.getExpensesById(1).get().getTotal());
//
//        Services services = new Services("PRZEGLĄD", LocalDate.now(), 500.5);
//
//        servicesService.saveServices(services);
//        System.out.println("Services zapisane do bazy");
//
//        System.out.println(servicesService.getServiceById(1).get().getTotal());
//
//        User user = new User("aaa", "bbb", "ccc");
//
//        userService.saveUser(user);
//        System.out.println("User zapisany do bazy");
//
//        System.out.println(userService.getUserById(1).get().getEmail());
//
//
//
//    }
//}
