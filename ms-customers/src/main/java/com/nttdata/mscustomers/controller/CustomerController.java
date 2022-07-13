package com.nttdata.mscustomers.controller;
import com.nttdata.mscustomers.model.Customer;
import com.nttdata.mscustomers.service.CustomerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private static final Logger log = LogManager.getLogger(CustomerController.class);
    @Autowired
    private CustomerService service;

    @GetMapping("/findAll")
    public Flux<Customer> getCustomers(){
        log.info("Service call FindAll customers"+ "OK");
        return service.findAll();
    }

    @GetMapping("/find/{id}")
    public Mono<Customer> getCustomer(@PathVariable String id){
        log.info("Service call find customer"+ "OK");
        Mono<Customer> newCustomer = service.findById(id);
        return newCustomer;
    }

    @PostMapping("/create")
    public Mono<Customer> createCustomer(@RequestBody Customer c){
        log.info("Service call create customer"+ "OK");
        Mono<Customer> newCustomer = service.save(c);
        return newCustomer;
    }

    @PutMapping("/update/{id}")
    public Mono<Customer> updateCustomer(@RequestBody Customer c, @PathVariable String id){
        log.info("Service call update customer"+ "OK");
        return service.update(c,id);
    }

    @DeleteMapping("/delete/{id}")
    public Mono<Customer> deleteCustomer(@PathVariable String id){
        log.info("Service call delete customer"+ "OK");
        return service.delete(id);
    }

}