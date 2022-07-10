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

    @GetMapping("/find/{id}")
    public Mono<Customer> getCustomer(@PathVariable String id){
        Mono<Customer> newCustomer = service.findById(id);
        return newCustomer;
    }

    @PostMapping("/create")
    public Mono<Customer> createCustomer(@RequestBody Customer c){
        Mono<Customer> newCustomer = service.save(c);
        return newCustomer;
    }

    @PutMapping("/update/{id}")
    public Mono<Customer> updateCustomer(@RequestBody Customer c, @PathVariable String id){
        return service.update(c,id);
    }

    @DeleteMapping("/delete/{id}")
    public Mono<Customer> deleteCustomer(@PathVariable String id){
        return service.delete(id);
    }

}