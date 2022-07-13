package com.nttdata.mspayments.controller;

import com.nttdata.mspayments.model.Payments;
import com.nttdata.mspayments.service.PaymentsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/payments")
public class PaymentsController {
    private static final Logger log = LogManager.getLogger(PaymentsController.class);
    @Autowired
    private PaymentsService service;

    @GetMapping("/findAll")
    public Flux<Payments> getPayments(){
        log.info("Service call FindAll payments"+ "OK");
        return service.findAll();
    }

    @GetMapping("/find/{id}")
    public Mono<Payments> getPayment(@PathVariable String id){
        log.info("Service call find payment"+ "OK");
        Mono<Payments> newPayments = service.findById(id);
        return newPayments;
    }

    @PostMapping("/create")
    public Mono<Payments> createPayments(@RequestBody Payments c){
        log.info("Service call create payment"+ "OK");
        Mono<Payments> newPayments = service.save(c);
        return newPayments;
    }

    @PutMapping("/update/{id}")
    public Mono<Payments> updatePayments(@RequestBody Payments c, @PathVariable String id){
        log.info("Service call update payment"+ "OK");
        return service.update(c,id);
    }

    @DeleteMapping("/delete/{id}")
    public Mono<Payments> deletePayments(@PathVariable String id){
        log.info("Service call delete payment"+ "OK");
        return service.delete(id);
    }
}
