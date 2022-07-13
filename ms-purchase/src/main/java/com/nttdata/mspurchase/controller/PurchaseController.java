package com.nttdata.mspurchase.controller;

import com.nttdata.mspurchase.model.Purchase;
import com.nttdata.mspurchase.service.PurchaseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/purchase")
public class PurchaseController {
    private static final Logger log = LogManager.getLogger(PurchaseController.class);
    @Autowired
    private PurchaseService service;

    @GetMapping("/findAll")
    public Flux<Purchase> getPurchases(){
        log.info("Service call FindAll purchase"+ "OK");
        return service.findAll();
    }

    @GetMapping("/find/{id}")
    public Mono<Purchase> getPurchase(@PathVariable String id){
        log.info("Service call find purchase"+ "OK");
        Mono<Purchase> newPurchase = service.findById(id);
        return newPurchase;
    }

    @PostMapping("/create")
    public Mono<Purchase> createShopping(@RequestBody Purchase c){
        log.info("Service call create purchase"+ "OK");
        Mono<Purchase> newPurchase = service.save(c);
        return newPurchase;
    }

    @PutMapping("/update/{id}")
    public Mono<Purchase> updatePurchase(@RequestBody Purchase c, @PathVariable String id){
        log.info("Service call update purchase"+ "OK");
        return service.update(c,id);
    }

    @DeleteMapping("/delete/{id}")
    public Mono<Purchase> deletePurchase(@PathVariable String id){
        log.info("Service call delete purchase"+ "OK");
        return service.delete(id);
    }
}
