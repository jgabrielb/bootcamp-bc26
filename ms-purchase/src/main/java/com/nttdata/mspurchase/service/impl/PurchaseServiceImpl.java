package com.nttdata.mspurchase.service.impl;

import com.nttdata.mspurchase.client.AccountClient;
import com.nttdata.mspurchase.model.Purchase;
import com.nttdata.mspurchase.repository.PurchaseRepository;
import com.nttdata.mspurchase.service.PurchaseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
@Transactional
public class PurchaseServiceImpl implements PurchaseService {

    private static Logger logger = LogManager.getLogger(PurchaseServiceImpl.class);

    @Autowired
    PurchaseRepository repository;

    @Autowired
    AccountClient accountClient;

    @Override
    public Flux<Purchase> findAll() {
        logger.info("Executing findAll method");
        return repository.findAll();
    }

    @Override
    public Mono<Purchase> save(Purchase c) {
        logger.info("Executing save method");
        return accountClient.getAccountWithDetails(c.getAccountId())
                .filter( x -> x.getProduct().getIndProduct() == 2 && x.getProduct().getTypeProduct() == 3) // Validar que el producto sea de tipo Activo y que el producto sea de Tarjeta de Crédito
                .filter(x -> x.getCreditActually().compareTo(c.getPurchaseAmount()) >= 0 ) //validar que el credito actual sea mayor o igual al monto de la compra
                .hasElement()
                .flatMap( y -> {
                    if(y){
                        return repository.save(c);
                    }else{
                        return Mono.error(new RuntimeException("Verificar que sea una tarjeta de crédito y que el monto de la compra no sobre pase el limite del credito"));
                    }
                });
    }

    @Override
    public Mono<Purchase> findById(String id) {
        logger.info("Executing findById method");
        return repository.findById(id);
    }

    @Override
    public Mono<Purchase> update(Purchase c, String id) {
        logger.info("Executing update method");
        return repository.findById(id)
                .flatMap( x -> {
                    x.setPurchaseDate(c.getPurchaseDate());
                    x.setPurchaseAmount(c.getPurchaseAmount());
                    x.setDescription(c.getDescription());
                    x.setCurrency(c.getCurrency());
                    x.setAccountId(c.getAccountId());
                    return repository.save(x);
                });
    }

    @Override
    public Mono<Purchase> delete(String id) {
        logger.info("Executing delete method");
        return repository.findById(id)
                .flatMap( x -> repository.delete(x)
                        .then(Mono.just(x)));
    }
}
