package com.nttdata.msproducts.Controller;

import com.nttdata.msproducts.model.Products;
import com.nttdata.msproducts.service.ProductsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
public class ProductsController {
    private static final Logger log = LogManager.getLogger(ProductsController.class);
    @Autowired
    private ProductsService service;

    @GetMapping("/findAll")
    public Flux<Products> getProducts(){
        log.info("Service call FindAll products"+ "OK");
        return service.findAll();
    }

    @GetMapping("/find/{id}")
    public Mono<Products> getProducts(@PathVariable String id){
        log.info("Service call find payments"+ "OK");
        Mono<Products> newProducts = service.findById(id);
        return newProducts;
    }

    @PostMapping("/create")
    public Mono<Products> createProducts(@RequestBody Products c){
        log.info("Service call create products"+ "OK");
        Mono<Products> newProducts = service.save(c);
        return newProducts;
    }

    @PutMapping("/update/{id}")
    public Mono<Products> updateProducts(@RequestBody Products c, @PathVariable String id){
        log.info("Service call update products"+ "OK");
        return service.update(c,id);
    }

    @DeleteMapping("/delete/{id}")
    public Mono<Products> deleteProducts(@PathVariable String id){
        log.info("Service call delete products"+ "OK");
        return service.delete(id);
    }

}
