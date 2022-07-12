package com.nttdata.msaccounts.repository;

import com.nttdata.msaccounts.model.Account;
import com.nttdata.msaccounts.model.Customer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Repository
public interface AccountRepository extends ReactiveCrudRepository<Account, String> {

}
