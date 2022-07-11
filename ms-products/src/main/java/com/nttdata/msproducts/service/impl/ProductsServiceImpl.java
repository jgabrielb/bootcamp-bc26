package com.nttdata.msproducts.service.impl;

import com.nttdata.msproducts.model.Products;
import com.nttdata.msproducts.repository.ProductsRepository;
import com.nttdata.msproducts.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductsServiceImpl implements ProductsService {
    @Autowired
    ProductsRepository repository;

    @Override
    public Flux<Products> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<Products> save(Products c) {
        return repository.save(c);
    }

    @Override
    public Mono<Products> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Mono<Products> update(Products c, String id) {

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
        return repository.findById(id).flatMap( x -> repository.delete(x).then(Mono.just(new Products())));
    }
}
