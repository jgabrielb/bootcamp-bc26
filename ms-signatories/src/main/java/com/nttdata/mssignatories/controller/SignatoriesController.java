package com.nttdata.mssignatories.controller;

import com.nttdata.mssignatories.model.Signatories;
import com.nttdata.mssignatories.service.SignatoriesService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/signatories")
public class SignatoriesController {
    private static final Logger log = LogManager.getLogger(SignatoriesController.class);
    @Autowired
    private SignatoriesService service;

    @GetMapping("/findAll")
    public Flux<Signatories> getSignatories(){
        log.info("Service call findAll signatories"+ "OK");
        return service.findAll();
    }

    @GetMapping("/find/{id}")
    public Mono<Signatories> getSignatorie(@PathVariable String id){
        log.info("Service call find signatories"+ "OK");
        Mono<Signatories> newSignatories = service.findById(id);
        return newSignatories;
    }

    @PostMapping("/create")
    public Mono<Signatories> createSignatories(@RequestBody Signatories c){
        log.info("Service call create signatories"+ "OK");
        Mono<Signatories> newSignatories = service.save(c);
        return newSignatories;
    }

    @PutMapping("/update/{id}")
    public Mono<Signatories> updateSignatories(@RequestBody Signatories c, @PathVariable String id){
        log.info("Service call update signatories"+ "OK");
        return service.update(c,id);
    }

    @DeleteMapping("/delete/{id}")
    public Mono<Signatories> deleteSignatories(@PathVariable String id){
        log.info("Service call delete signatories"+ "OK");
        return service.delete(id);
    }
}
