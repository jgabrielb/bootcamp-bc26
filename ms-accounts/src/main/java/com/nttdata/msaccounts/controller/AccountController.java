package com.nttdata.msaccounts.controller;

import com.nttdata.msaccounts.controller.model.Account;
import com.nttdata.msaccounts.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.invoke.MethodHandles;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private static final Logger log = LogManager.getLogger(AccountController.class);
    @Autowired
    AccountService service;

    @GetMapping("/findAll")
    public Flux<Account> getAccounts(){
        log.info("Service call FindAll accounts"+ "OK");
        return service.findAll();
    }

    @GetMapping("/find/{id}")
    public Mono<Account> getAccount(@PathVariable String id){
        log.info("Service call FindById accounts"+ "OK");
        Mono<Account> newAccount = service.findById(id);
        return newAccount;
    }
    @GetMapping("/findWithCustomer/{id}")
    public Mono<Account> getAccountWithCustomer(@PathVariable String id){
        log.info("Service call findWithCustomer accounts"+ "OK");
        Mono<Account> newAccount = service.findByIdWithCostumer(id);
        return newAccount;
    }
    @PostMapping("/create")
    public Mono<Account> createAccount(@RequestBody Account a){
        log.info("Service call Create accounts"+ "OK");
        Mono<Account> newAccount = service.save(a);
        return newAccount;
    }

    @PutMapping("/update/{id}")
    public Mono<Account> updateAccount(@RequestBody Account a, @PathVariable String id){
        log.info("Service call Update accounts"+ "OK");
        return service.update(a,id);
    }

    @DeleteMapping("/delete/{id}")
    public Mono<Account> deleteAccount(@PathVariable String id){
        log.info("Service call Delete accounts" + "OK");
        return service.delete(id);
    }
}
