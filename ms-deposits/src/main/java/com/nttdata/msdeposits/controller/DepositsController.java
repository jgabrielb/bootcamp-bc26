package com.nttdata.msdeposits.controller;

import com.nttdata.msdeposits.model.Deposits;
import com.nttdata.msdeposits.service.DepositsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/deposits")
public class DepositsController {
    @Autowired
    private DepositsService service;

    @GetMapping("/findAll")
    public Flux<Deposits> getDeposits(){
        return service.findAll();
    }

    @GetMapping("/find/{id}")
    public Mono<Deposits> getDeposit(@PathVariable String id){
        Mono<Deposits> newDeposits = service.findById(id);
        return newDeposits;
    }

    @PostMapping("/create")
    public Mono<Deposits> createDeposits(@RequestBody Deposits c){
        Mono<Deposits> newDeposits = service.save(c);
        return newDeposits;
    }

    @PutMapping("/update/{id}")
    public Mono<Deposits> updateDeposits(@RequestBody Deposits c, @PathVariable String id){
        return service.update(c,id);
    }

    @DeleteMapping("/delete/{id}")
    public Mono<Deposits> deleteDeposits(@PathVariable String id){
        return service.delete(id);
    }
}
