package com.nttdata.msaccounts.service.impl;

import com.nttdata.msaccounts.client.CustomerClient;
import com.nttdata.msaccounts.controller.model.Account;
import com.nttdata.msaccounts.repository.AccountRepository;
import com.nttdata.msaccounts.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepository repository;

    @Autowired
    CustomerClient customerClient;

    @Override
    public Flux<Account> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<Account> save(Account a) {
        return repository.save(a);
    }

    @Override
    public Mono<Account> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Mono<Account> update(Account a, String id) {
        return repository.findById(id)
                .map( x -> {
                    x.setProductId(a.getProductId());
                    x.setAccountNumber(a.getAccountNumber());
                    x.setAccountNumberInt(a.getAccountNumberInt());
                    x.setMovementLimits(a.getMovementLimits());
                    x.setMovementActually(a.getMovementActually());
                    x.setCreditLimits(a.getCreditLimits());
                    x.setCreditActually(a.getCreditActually());
                    x.setMovementDate(a.getMovementDate());
                    return x;
                }).flatMap(repository::save);
    }

    @Override
    public Mono<Account> delete(String id) {
        return repository.findById(id).flatMap( x -> repository.delete(x).then(Mono.just(new Account())));
    }

    @Override
    public Mono<Account> findByIdWithCostumer(String id) {
        return repository.findById(id).map( x -> {
            x.setCustomer(customerClient.getCustomer(x.getCustomerId()).block());
            return x;
        });
    }
}
