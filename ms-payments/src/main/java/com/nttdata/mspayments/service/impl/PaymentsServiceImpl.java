package com.nttdata.mspayments.service.impl;

import com.nttdata.mspayments.model.Payments;
import com.nttdata.mspayments.repository.PaymentsRepository;
import com.nttdata.mspayments.service.PaymentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PaymentsServiceImpl implements PaymentsService {
    @Autowired
    PaymentsRepository repository;

    @Override
    public Flux<Payments> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<Payments> save(Payments c) {
        return repository.save(c);
    }

    @Override
    public Mono<Payments> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Mono<Payments> update(Payments c, String id) {
        return repository.findById(id)
                .map( x -> {
                    x.setPaymentDate(c.getPaymentDate());
                    x.setPaymentAmount(c.getPaymentAmount());
                    x.setDescription(c.getDescription());
                    return x;
                }).flatMap(repository::save);
    }

    @Override
    public Mono<Payments> delete(String id) {
        return repository.findById(id).flatMap( x -> repository.delete(x).then(Mono.just(new Payments())));
    }
}
