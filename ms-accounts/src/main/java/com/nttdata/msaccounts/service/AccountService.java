package com.nttdata.msaccounts.service;

import com.nttdata.msaccounts.model.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountService {
    Flux<Account> findAll();

    Mono<Account> save(Account a);

    Mono<Account> findById(String id);

    Mono<Account> update(Account a, String id);

    Mono<Account> delete(String id);
}
