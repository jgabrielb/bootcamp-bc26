package com.nttdata.mspurchase.service.impl;

import com.nttdata.mspurchase.model.Purchase;
import com.nttdata.mspurchase.repository.PurchaseRepository;
import com.nttdata.mspurchase.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PurchaseServiceImpl implements PurchaseService {
    @Autowired
    PurchaseRepository repository;

    @Override
    public Flux<Purchase> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<Purchase> save(Purchase c) {
        return repository.save(c);
    }

    @Override
    public Mono<Purchase> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Mono<Purchase> update(Purchase c, String id) {
        return repository.findById(id)
                .map( x -> {
                    x.setPurchaseDate(c.getPurchaseDate());
                    x.setPurchaseAmount(c.getPurchaseAmount());
                    x.setDescription(c.getDescription());
                    return x;
                }).flatMap(repository::save);
    }

    @Override
    public Mono<Purchase> delete(String id) {
        return repository.findById(id).flatMap( x -> repository.delete(x).then(Mono.just(new Purchase())));
    }
}
