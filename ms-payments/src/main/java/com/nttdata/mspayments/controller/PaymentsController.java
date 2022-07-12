package com.nttdata.mspayments.controller;

import com.nttdata.mspayments.model.Payments;
import com.nttdata.mspayments.service.PaymentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/deposits")
public class PaymentsController {
    @Autowired
    private PaymentsService service;

    @GetMapping("/findAll")
    public Flux<Payments> getPayments(){
        return service.findAll();
    }

    @GetMapping("/find/{id}")
    public Mono<Payments> getPayment(@PathVariable String id){
        Mono<Payments> newPayments = service.findById(id);
        return newPayments;
    }

    @PostMapping("/create")
    public Mono<Payments> createPayments(@RequestBody Payments c){
        Mono<Payments> newPayments = service.save(c);
        return newPayments;
    }

    @PutMapping("/update/{id}")
    public Mono<Payments> updatePayments(@RequestBody Payments c, @PathVariable String id){
        return service.update(c,id);
    }

    @DeleteMapping("/delete/{id}")
    public Mono<Payments> deletePayments(@PathVariable String id){
        return service.delete(id);
    }
}
