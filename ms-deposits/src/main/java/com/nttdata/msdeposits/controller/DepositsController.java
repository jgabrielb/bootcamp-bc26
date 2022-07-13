package com.nttdata.msdeposits.controller;

import com.nttdata.msdeposits.model.Deposits;
import com.nttdata.msdeposits.service.DepositsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/deposits")
public class DepositsController {
    private static final Logger log = LogManager.getLogger(DepositsController.class);
    @Autowired
    private DepositsService service;

    @GetMapping("/findAll")
    public Flux<Deposits> getDeposits(){
        log.info("Service call FindAll deposits"+ "OK");
        return service.findAll();
    }

    @GetMapping("/find/{id}")
    public Mono<Deposits> getDeposit(@PathVariable String id){
        log.info("Service call find deposits"+ "OK");
        Mono<Deposits> newDeposits = service.findById(id);
        return newDeposits;
    }

    @PostMapping("/create")
    public Mono<Deposits> createDeposits(@RequestBody Deposits c){
        log.info("Service call create deposits"+ "OK");
        Mono<Deposits> newDeposits = service.save(c);
        return newDeposits;
    }

    @PutMapping("/update/{id}")
    public Mono<Deposits> updateDeposits(@RequestBody Deposits c, @PathVariable String id){
        log.info("Service call update deposits"+ "OK");
        return service.update(c,id);
    }

    @DeleteMapping("/delete/{id}")
    public Mono<Deposits> deleteDeposits(@PathVariable String id){
        log.info("Service call delete deposits"+ "OK");
        return service.delete(id);
    }
}
