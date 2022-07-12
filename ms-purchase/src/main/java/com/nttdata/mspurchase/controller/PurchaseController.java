package com.nttdata.mspurchase.controller;

import com.nttdata.mspurchase.model.Purchase;
import com.nttdata.mspurchase.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/purchase")
public class PurchaseController {
    @Autowired
    private PurchaseService service;

    @GetMapping("/findAll")
    public Flux<Purchase> getPurchases(){
        return service.findAll();
    }

    @GetMapping("/find/{id}")
    public Mono<Purchase> getPurchase(@PathVariable String id){
        Mono<Purchase> newPurchase = service.findById(id);
        return newPurchase;
    }

    @PostMapping("/create")
    public Mono<Purchase> createShopping(@RequestBody Purchase c){
        Mono<Purchase> newPurchase = service.save(c);
        return newPurchase;
    }

    @PutMapping("/update/{id}")
    public Mono<Purchase> updatePurchase(@RequestBody Purchase c, @PathVariable String id){
        return service.update(c,id);
    }

    @DeleteMapping("/delete/{id}")
    public Mono<Purchase> deletePurchase(@PathVariable String id){
        return service.delete(id);
    }
}
