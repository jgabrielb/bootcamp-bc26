package com.nttdata.mssignatories.service.impl;

import com.nttdata.mssignatories.model.Signatories;
import com.nttdata.mssignatories.repository.SignatoriesRepository;
import com.nttdata.mssignatories.service.SignatoriesService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class SignatoriesServiceImpl implements SignatoriesService {
    private static final Logger log = LogManager.getLogger(SignatoriesServiceImpl.class);
    @Autowired
    SignatoriesRepository repository;

    @Override
    public Flux<Signatories> findAll() {
        log.info("Method call findAll signatories"+ "OK");
        return repository.findAll();
    }

    @Override
    public Mono<Signatories> save(Signatories c) {
        log.info("Method call save signatories"+ "OK");
        return repository.save(c);
    }

    @Override
    public Mono<Signatories> findById(String id) {
        log.info("Method call findById signatories"+ "OK");
        return repository.findById(id);
    }

    @Override
    public Mono<Signatories> update(Signatories c, String id) {
        log.info("Method call update signatories"+ "OK");
        return repository.findById(id)
                .map( x -> {
                    x.setFirstName(c.getFirstName());
                    x.setLastName(c.getLastName());
                    x.setDocNumber(c.getDocNumber());
                    x.setAccountId(c.getAccountId());
                    return x;
                }).flatMap(repository::save);
    }

    @Override
    public Mono<Signatories> delete(String id) {
        log.info("Method call delete signatories"+ "OK");
        return repository.findById(id).flatMap( x -> repository.delete(x).then(Mono.just(new Signatories())));
    }
}
