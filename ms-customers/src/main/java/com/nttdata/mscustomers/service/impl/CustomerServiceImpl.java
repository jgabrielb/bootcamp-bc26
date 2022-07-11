package com.nttdata.mscustomers.service.impl;

import com.nttdata.mscustomers.model.Customer;
import com.nttdata.mscustomers.repository.CustomerRepository;
import com.nttdata.mscustomers.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository repository;

    @Override
    public Flux<Customer> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<Customer> save(Customer c) {
        return repository.save(c);
    }

    @Override
    public Mono<Customer> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Mono<Customer> update(Customer c, String id) {
        return repository.findById(id)
                .map( x -> {
                            x.setFirstName(c.getFirstName());
                            x.setLastName(c.getLastName());
                            x.setDocNumber(c.getDocNumber());
                            x.setTypeCustomer(c.getTypeCustomer());
                            x.setDescTypeCustomer(c.getDescTypeCustomer());
                            return x;
                }).flatMap(repository::save);
    }

    @Override
    public Mono<Customer> delete(String id) {
        return repository.findById(id).flatMap( x -> repository.delete(x).then(Mono.just(new Customer())));
    }
}
