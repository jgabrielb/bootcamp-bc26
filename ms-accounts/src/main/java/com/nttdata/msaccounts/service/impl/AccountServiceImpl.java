package com.nttdata.msaccounts.service.impl;

import com.nttdata.msaccounts.client.ProductClient;
import com.nttdata.msaccounts.model.Customer;
import com.nttdata.msaccounts.model.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.nttdata.msaccounts.client.CustomerClient;
import com.nttdata.msaccounts.model.Account;
import com.nttdata.msaccounts.repository.AccountRepository;
import com.nttdata.msaccounts.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private static Logger logger = LogManager.getLogger(AccountServiceImpl.class);

    @Autowired
    AccountRepository repository;

    @Autowired
    CustomerClient customerClient;

    @Autowired
    ProductClient productClient;

    @Override
    public Flux<Account> findAll() {
        logger.info("Executing findAll method");
        return repository.findAll();
    }

    @Override
    public Flux<Account> findAllWithDetail() {
        logger.info("Executing findAllWithDetail method");
        return repository.findAll()
                .flatMap( x -> {
                   return customerClient.getCustomer(x.getCustomerId())
                           .flatMapMany( y -> {
                               return productClient.getProduct(x.getProductId())
                                       .flatMapMany( z -> {
                                           x.setCustomer(y);
                                           x.setProduct(z);
                                           return Flux.just(x);
                                       });
                           });
                });
    }

    @Override
    public Mono<Account> save(Account a) {
        logger.info("Executing save method");

        return this.findAllWithDetail()
                .filter( x -> x.getCustomerId().equals(a.getCustomerId())) // Buscamos el customerId de la lista
                .filter( x -> (x.getProduct().getIndProduct() == 1 && x.getCustomer().getTypeCustomer() == 1) && (x.getProduct().getId().equals(a.getProductId())) ) // Buscamos si tiene una cuenta bancaria y es cliente personal
                .hasElements()
                .flatMap( v -> {
                    if (v){
                        return Mono.error(new RuntimeException("El cliente personal no puede tener mas de una cuenta bancaria"));
                    }else{
                        return this.findAllWithDetail()
                                .filter( x -> x.getCustomerId().equals(a.getCustomerId())) // Buscamos el customerId de la lista
                                .filter( x -> (x.getProduct().getIndProduct() == 2 && x.getCustomer().getTypeCustomer() == 1) && (x.getProduct().getId().equals(a.getProductId())) ) // Buscamos si tiene un credito y es cliente personal
                                .hasElements()
                                .flatMap( w -> {
                                   if (w){
                                       return Mono.error(new RuntimeException("El cliente personal no puede tener mas de un credito"));
                                   }else{
                                       return productClient.getProduct(a.getProductId())
                                               .filter( x -> (x.getIndProduct() == 1) ) // Validar si el producto es PASIVO
                                               .filter( x -> (x.getTypeProduct() == 1 || x.getTypeProduct() == 3) ) // Validar si es cuenta de ahorros o plazo fijo
                                               .hasElement()
                                               .flatMap( zz -> {
                                                   return customerClient.getCustomer(a.getCustomerId())
                                                           .filter( (x -> x.getTypeCustomer() == 2) ) // Validar si el customerId es empresarial
                                                           .hasElement()
                                                           .flatMap( yy -> {
                                                               if ( zz != null && yy != null){
                                                                   return Mono.error(new RuntimeException("El cliente empresarial no puede tener una cuenta de ahorros o plazo fijo"));
                                                               }else{
                                                                   return repository.save(a);
                                                               }
                                                           });
                                               });
                                   }
                                });
                    }
                });
    }

    @Override
    public Mono<Account> findById(String id) {
        logger.info("Executing findById method");
        return repository.findById(id);
    }

    @Override
    public Mono<Account> findByIdWithDetail(String id) {
        logger.info("Executing findByIdWithDetail method");
        return repository.findById(id)
                .flatMap( x -> {
                    return customerClient.getCustomer(x.getCustomerId())
                            .flatMap( y -> {
                                return productClient.getProduct(x.getProductId())
                                        .flatMap( z -> {
                                            x.setCustomer(y);
                                            x.setProduct(z);
                                            return Mono.just(x);
                                        });
                            });
                });
    }

    @Override
    public Mono<Account> update(Account a, String id) {
        logger.info("Executing update method");
        return repository.findById(id)
                .flatMap( x -> {
                    x.setProductId(a.getProductId());
                    x.setAccountNumber(a.getAccountNumber());
                    x.setAccountNumberInt(a.getAccountNumberInt());
                    x.setMovementLimits(a.getMovementLimits());
                    x.setMovementActually(a.getMovementActually());
                    x.setCreditLimits(a.getCreditLimits());
                    x.setCreditActually(a.getCreditActually());
                    x.setMovementDate(a.getMovementDate());
                    return repository.save(x);
                });
    }

    @Override
    public Mono<Account> delete(String id) {
        logger.info("Executing delete method");
        return repository.findById(id)
                .flatMap( x -> repository.delete(x)
                        .then(Mono.just(x)));
    }

}
