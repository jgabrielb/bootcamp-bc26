package com.nttdata.mswithdrawals.controller;

import com.nttdata.mswithdrawals.model.Withdrawals;
import com.nttdata.mswithdrawals.service.WithdrawalsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/withdrawals")
public class WithdrawalsController {
    private static final Logger log = LogManager.getLogger(WithdrawalsController.class);
    @Autowired
    private WithdrawalsService service;

    @GetMapping("/findAll")
    public Flux<Withdrawals> getWithdrawals(){
        log.info("Service call findAll withdrawals"+ "OK");
        return service.findAll();
    }

    @GetMapping("/find/{id}")
    public Mono<Withdrawals> getWithdrawal(@PathVariable String id){
        log.info("Service call find withdrawal"+ "OK");
        Mono<Withdrawals> newWithdrawals = service.findById(id);
        return newWithdrawals;
    }

    @PostMapping("/create")
    public Mono<Withdrawals> createWithdrawals(@RequestBody Withdrawals c){
        log.info("Service call create withdrawal"+ "OK");
        Mono<Withdrawals> newWithdrawals = service.save(c);
        return newWithdrawals;
    }

    @PutMapping("/update/{id}")
    public Mono<Withdrawals> updateWithdrawals(@RequestBody Withdrawals c, @PathVariable String id){
        log.info("Service call update withdrawal"+ "OK");
        return service.update(c,id);
    }

    @DeleteMapping("/delete/{id}")
    public Mono<Withdrawals> deleteWithdrawals(@PathVariable String id){
        log.info("Service call delete withdrawal"+ "OK");
        return service.delete(id);
    }
}
