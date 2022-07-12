package com.nttdata.mswithdrawals.controller;

import com.nttdata.mswithdrawals.model.Withdrawals;
import com.nttdata.mswithdrawals.service.WithdrawalsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/withdrawals")
public class WithdrawalsController {
    @Autowired
    private WithdrawalsService service;

    @GetMapping("/findAll")
    public Flux<Withdrawals> getWithdrawals(){
        return service.findAll();
    }

    @GetMapping("/find/{id}")
    public Mono<Withdrawals> getWithdrawal(@PathVariable String id){
        Mono<Withdrawals> newWithdrawals = service.findById(id);
        return newWithdrawals;
    }

    @PostMapping("/create")
    public Mono<Withdrawals> createWithdrawals(@RequestBody Withdrawals c){
        Mono<Withdrawals> newWithdrawals = service.save(c);
        return newWithdrawals;
    }

    @PutMapping("/update/{id}")
    public Mono<Withdrawals> updateWithdrawals(@RequestBody Withdrawals c, @PathVariable String id){
        return service.update(c,id);
    }

    @DeleteMapping("/delete/{id}")
    public Mono<Withdrawals> deleteWithdrawals(@PathVariable String id){
        return service.delete(id);
    }
}
