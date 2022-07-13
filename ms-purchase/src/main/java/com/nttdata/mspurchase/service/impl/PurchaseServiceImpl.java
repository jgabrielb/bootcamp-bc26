package com.nttdata.mspurchase.service.impl;

import com.nttdata.mspurchase.model.Purchase;
import com.nttdata.mspurchase.repository.PurchaseRepository;
import com.nttdata.mspurchase.service.PurchaseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PurchaseServiceImpl implements PurchaseService {
    private static final Logger log = LogManager.getLogger(PurchaseServiceImpl.class);
    @Autowired
    PurchaseRepository repository;

    @Override
    public Flux<Purchase> findAll() {
        log.info("Method call findAll purchase"+ "OK");
        return repository.findAll();
    }

    @Override
    public Mono<Purchase> save(Purchase c) {
        log.info("Method call save purchase"+ "OK");
        return repository.save(c);
    }

    @Override
    public Mono<Purchase> findById(String id) {
        log.info("Method call findById purchase"+ "OK");
        return repository.findById(id);
    }

    @Override
    public Mono<Purchase> update(Purchase c, String id) {
        log.info("Method call update purchase"+ "OK");
        return repository.findById(id)
                .map( x -> {
                    x.setPurchaseDate(c.getPurchaseDate());
                    x.setPurchaseAmount(c.getPurchaseAmount());
                    x.setDescription(c.getDescription());
                    x.setCurrency(c.getCurrency());
                    x.setAccountId(c.getAccountId());
                    return x;
                }).flatMap(repository::save);
    }

    @Override
    public Mono<Purchase> delete(String id) {
        log.info("Method call delete purchase"+ "OK");
        return repository.findById(id).flatMap( x -> repository.delete(x).then(Mono.just(new Purchase())));
    }
}
