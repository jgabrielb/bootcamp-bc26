package com.nttdata.msdeposits.service.impl;

import com.nttdata.msdeposits.model.Deposits;
import com.nttdata.msdeposits.repository.DepositsRepository;
import com.nttdata.msdeposits.service.DepositsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DepositsServiceImpl implements DepositsService{
    @Autowired
    DepositsRepository repository;

    @Override
    public Flux<Deposits> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<Deposits> save(Deposits c) {
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
                    x.setDescription(c.getDescription());
                    return x;
                }).flatMap(repository::save);
    }

    @Override
    public Mono<Deposits> delete(String id) {
        return repository.findById(id).flatMap( x -> repository.delete(x).then(Mono.just(new Deposits())));
    }
}
