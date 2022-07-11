package com.nttdata.mscustomers.controller;

import com.nttdata.mscustomers.model.Customer;
import com.nttdata.mscustomers.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService service;

    @GetMapping("/findAll")
    public Flux<Customer> getCustomers(){
        return service.findAll();
    }

    @PostMapping("/create")
    public Mono<Customer> createCustomer(@RequestBody Customer c){
        Mono<Customer> newCustomer = service.create(c);
        return newCustomer;
    }

}
