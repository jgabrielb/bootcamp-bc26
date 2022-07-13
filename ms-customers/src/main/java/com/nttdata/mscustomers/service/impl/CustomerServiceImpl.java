package com.nttdata.mscustomers.service.impl;

import com.nttdata.mscustomers.model.Customer;
import com.nttdata.mscustomers.repository.CustomerRepository;
import com.nttdata.mscustomers.service.CustomerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerServiceImpl implements CustomerService {
    private static final Logger log = LogManager.getLogger(CustomerServiceImpl.class);
    @Autowired
    CustomerRepository repository;

    @Override
    public Flux<Customer> findAll() {
        log.info("Method call FindAll customers"+ "OK");
        return repository.findAll();
    }

    @Override
    public Mono<Customer> save(Customer c) {
        log.info("Method call save customer"+ "OK");
        return repository.save(c);
    }

    @Override
    public Mono<Customer> findById(String id) {
        log.info("Method call findById customer"+ "OK");
        return repository.findById(id);
    }

    @Override
    public Mono<Customer> update(Customer c, String id) {
        log.info("Method call update customer"+ "OK");
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
        log.info("Method call delete customer"+ "OK");
        return repository.findById(id).flatMap( x -> repository.delete(x).then(Mono.just(new Customer())));
    }
}
