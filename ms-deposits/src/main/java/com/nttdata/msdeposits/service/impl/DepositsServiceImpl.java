package com.nttdata.msdeposits.service.impl;

import com.nttdata.msdeposits.model.Account;
import com.nttdata.msdeposits.model.Deposits;
import com.nttdata.msdeposits.repository.DepositsRepository;
import com.nttdata.msdeposits.service.DepositsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DepositsServiceImpl implements DepositsService{
    @Autowired
    DepositsRepository repository;

    //@Autowired
    //AccountClient accountClient;

    @Override
    public Flux<Deposits> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<Deposits> save(Deposits c) {
        /*
        Account account = accountClient.getAccountDetails(c.getAccountId())
                .filter( x -> x.getProduct().getIndProduct() == 1)
                .share()
                .block();
        */

        //Account account = accountClient.getAccountDetails(c.getAccountId());

        //if(account != null){
        //    return repository.save(c);
        //}else{
        //    throw new RuntimeException("no se puede depositar a un cuenta de credito");
        //}
        return repository.save(c);

    }

    @Override
    public Mono<Deposits> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Mono<Deposits> update(Deposits c, String id) {
        return repository.findById(id)
                .map( x -> {
                    x.setDepositDate(c.getDepositDate());
                    x.setDepositAmount(c.getDepositAmount());
                    x.setCurrency(c.getCurrency());
                    x.setAccountId(c.getAccountId());
                    return x;
                }).flatMap(repository::save);
    }

    @Override
    public Mono<Deposits> delete(String id) {
        return repository.findById(id).flatMap( x -> repository.delete(x).then(Mono.just(new Deposits())));
    }
}
