package com.example.servicecustomer.controller;

import com.example.servicecustomer.model.Customer;
import com.example.servicecustomer.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // Endpoint pour obtenir tous les clients
    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    // Endpoint pour obtenir un client par ID
    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    // Endpoint pour créer ou mettre à jour un client
    @PostMapping
    public Customer createOrUpdateCustomer(@RequestBody Customer customer) {
        return customerService.saveCustomer(customer);
    }

    // Endpoint pour supprimer un client par ID
    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
    }
}