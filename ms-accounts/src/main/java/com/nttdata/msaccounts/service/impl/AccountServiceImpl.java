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
        return repository.findAll().map( accounts -> {
            accounts.setCustomer(customerClient.getCustomer(accounts.getCustomerId()).block());
            accounts.setProduct(productClient.getProduct(accounts.getProductId()).block());
            return accounts;
        });
    }

    @Override
    public Mono<Account> save(Account a) {
        logger.info("Executing save method");

        // Validando si el cliente personal ya posee una cuenta bancaria
        long valPersonalCustomer = this.findAllWithDetail()
                .filter( x -> x.getCustomerId().equals(a.getCustomerId()))
                .filter( x -> (x.getProduct().getIndProduct() == 1 && x.getCustomer().getTypeCustomer() == 1) )
                .count()
                .share()
                .block();

        if (valPersonalCustomer > 0) {
            throw new RuntimeException("El cliente personal no puede tener mas de una cuenta bancaria");
        }

        // Validando si el cliente personal ya posee un credito
        long valPersonalCustomer2 = this.findAllWithDetail()
                .filter( x -> x.getCustomerId().equals(a.getCustomerId()))
                .filter( x -> (x.getProduct().getIndProduct() == 2 && x.getCustomer().getTypeCustomer() == 1) )
                .count()
                .share()
                .block();

        if (valPersonalCustomer2 > 0) {
            throw new RuntimeException("El cliente personal no puede tener mas de un credito");
        }

        // Obteniendo los datos del producto solicitado a crear
        Product nProduct = productClient.getProduct(a.getProductId())
                .filter( y -> (y.getIndProduct() == 1) ) // Validar si el producto es PASIVO
                .filter( y -> (y.getTypeProduct() == 1 || y.getTypeProduct() == 3) ) // Validar si es cuenta de ahorros o plazo fijo
                .share()
                .block();

        // Obteniendo los datos del cliente solicitante
        Customer nCustomer = customerClient.getCustomer(a.getCustomerId())
                .filter( (z -> z.getTypeCustomer() == 2) ) // Validar si el customerId es empresarial
                .share()
                .block();

        if (nProduct != null && nCustomer != null) {
            throw new RuntimeException("El cliente empresarial no puede tener una cuenta de ahorros o plazo fijo");
        }

        return repository.save(a);
    }

    @Override
    public Mono<Account> findById(String id) {
        logger.info("Executing findById method");
        return repository.findById(id);
    }

    @Override
    public Mono<Account> findByIdWithDetail(String id) {
        logger.info("Executing findByIdWithDetail method");
        return repository.findById(id).map( x -> {
            x.setCustomer(customerClient.getCustomer(x.getCustomerId()).block());
            x.setProduct(productClient.getProduct(x.getProductId()).block());
            return x;
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

    @Override
    public Mono<Account> findByIdWithCostumer(String id) {
        logger.info("Executing findByIdWithCostumer method");
        return repository.findById(id).map( x -> {
            x.setCustomer(customerClient.getCustomer(x.getCustomerId()).block());
            x.setProduct(productClient.getProduct(x.getProductId()).block());
            return x;
        });
    }
}
