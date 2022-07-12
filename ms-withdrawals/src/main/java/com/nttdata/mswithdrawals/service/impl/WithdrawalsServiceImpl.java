package com.nttdata.mswithdrawals.service.impl;

import com.nttdata.mswithdrawals.model.Withdrawals;
import com.nttdata.mswithdrawals.repository.WithdrawalsRepository;
import com.nttdata.mswithdrawals.service.WithdrawalsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class WithdrawalsServiceImpl implements WithdrawalsService {
    @Autowired
    WithdrawalsRepository repository;

    @Override
    public Flux<Withdrawals> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<Withdrawals> save(Withdrawals c) {
        return repository.save(c);
    }

    @Override
    public Mono<Withdrawals> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Mono<Withdrawals> update(Withdrawals c, String id) {
        return repository.findById(id)
                .map( x -> {
                    x.setWithdrawalsDate(c.getWithdrawalsDate());
                    x.setWithdrawalsAmount(c.getWithdrawalsAmount());
                    x.setDescription(c.getDescription());
                    return x;
                }).flatMap(repository::save);
    }

    @Override
    public Mono<Withdrawals> delete(String id) {
        return repository.findById(id).flatMap( x -> repository.delete(x).then(Mono.just(new Withdrawals())));
    }
}
