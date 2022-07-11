package com.nttdata.msaccounts.controller;

import com.nttdata.msaccounts.model.Account;
import com.nttdata.msaccounts.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    AccountService service;

    @GetMapping("/findAll")
    public Flux<Account> getCustomers(){
        return service.findAll();
    }

    @GetMapping("/find/{id}")
    public Mono<Account> getCustomer(@PathVariable String id){
        Mono<Account> newAccount = service.findById(id);
        return newAccount;
    }

    @PostMapping("/create")
    public Mono<Account> createCustomer(@RequestBody Account a){
        Mono<Account> newAccount = service.save(a);
        return newAccount;
    }

    @PutMapping("/update/{id}")
    public Mono<Account> updateCustomer(@RequestBody Account a, @PathVariable String id){
        return service.update(a,id);
    }

    @DeleteMapping("/delete/{id}")
    public Mono<Account> deleteCustomer(@PathVariable String id){
        return service.delete(id);
    }
}
