package com.nttdata.msproducts.service.impl;

import com.nttdata.msproducts.model.Products;
import com.nttdata.msproducts.repository.ProductsRepository;
import com.nttdata.msproducts.service.ProductsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductsServiceImpl implements ProductsService {
    private static final Logger log = LogManager.getLogger(ProductsServiceImpl.class);
    @Autowired
    ProductsRepository repository;

    @Override
    public Flux<Products> findAll() {
        log.info("Method call findAll products"+ "OK");
        return repository.findAll();
    }

    @Override
    public Mono<Products> save(Products c) {
        log.info("Method call save product"+ "OK");
        return repository.save(c);
    }

    @Override
    public Mono<Products> findById(String id) {
        log.info("Method call findById product"+ "OK");
        return repository.findById(id);
    }

    @Override
    public Mono<Products> update(Products c, String id) {
        log.info("Method call update product"+ "OK");

        return repository.findById(id)
                .map( x -> {
                    x.setIndProduct(c.getIndProduct());
                    x.setDescIndProduct(c.getDescIndProduct());
                    x.setTypeProduct(c.getTypeProduct());
                    x.setDescTypeProduct(c.getDescTypeProduct());
                    return x;
                }).flatMap(repository::save);
    }

    @Override
    public Mono<Products> delete(String id) {
        log.info("Method call delete product"+ "OK");
        return repository.findById(id).flatMap( x -> repository.delete(x).then(Mono.just(new Products())));
    }
}
