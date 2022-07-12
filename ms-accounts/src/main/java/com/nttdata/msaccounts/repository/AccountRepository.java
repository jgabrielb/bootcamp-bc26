package com.nttdata.msaccounts.repository;

import com.nttdata.msaccounts.controller.model.Account;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends ReactiveCrudRepository<Account, String> {

}
