package com.nttdata.mswithdrawals.service.impl;

import com.nttdata.mswithdrawals.model.Withdrawals;
import com.nttdata.mswithdrawals.repository.WithdrawalsRepository;
import com.nttdata.mswithdrawals.service.WithdrawalsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class WithdrawalsServiceImpl implements WithdrawalsService {
    private static final Logger log = LogManager.getLogger(WithdrawalsServiceImpl.class);
    @Autowired
    WithdrawalsRepository repository;

    @Override
    public Flux<Withdrawals> findAll() {
        log.info("Method call findAll withdrawals"+ "OK");
        return repository.findAll();
    }

    @Override
    public Mono<Withdrawals> save(Withdrawals c) {
        log.info("Method call save withdrawal"+ "OK");
        return repository.save(c);
    }

    @Override
    public Mono<Withdrawals> findById(String id) {
        log.info("Method call findById withdrawal"+ "OK");
        return repository.findById(id);
    }

    @Override
    public Mono<Withdrawals> update(Withdrawals c, String id) {
        log.info("Method call update withdrawals"+ "OK");
        return repository.findById(id)
                .map( x -> {
                    x.setWithdrawalsDate(c.getWithdrawalsDate());
                    x.setWithdrawalsAmount(c.getWithdrawalsAmount());
                    x.setCurrency(c.getCurrency());
                    x.setAccountId(c.getAccountId());
                    return x;
                }).flatMap(repository::save);
    }

    @Override
    public Mono<Withdrawals> delete(String id) {
        log.info("Method call delete withdrawals"+ "OK");
        return repository.findById(id).flatMap( x -> repository.delete(x).then(Mono.just(new Withdrawals())));
    }
}
