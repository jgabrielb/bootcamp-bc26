package com.nttdata.msdeposits.service.impl;

import com.nttdata.msdeposits.model.Deposits;
import com.nttdata.msdeposits.repository.DepositsRepository;
import com.nttdata.msdeposits.service.DepositsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DepositsServiceImpl implements DepositsService{
    private static final Logger log = LogManager.getLogger(DepositsServiceImpl.class);
    @Autowired
    DepositsRepository repository;

    @Override
    public Flux<Deposits> findAll() {
        log.info("Method call findAll deposits"+ "OK");
        return repository.findAll();
    }

    @Override
    public Mono<Deposits> save(Deposits c) {
        log.info("Method call save deposits"+ "OK");
        return repository.save(c);
    }

    @Override
    public Mono<Deposits> findById(String id) {
        log.info("Method call findById deposits"+ "OK");
        return repository.findById(id);
    }

    @Override
    public Mono<Deposits> update(Deposits c, String id) {
        log.info("Method call update deposits"+ "OK");
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
        log.info("Method call delete deposits"+ "OK");
        return repository.findById(id).flatMap( x -> repository.delete(x).then(Mono.just(new Deposits())));
    }
}
