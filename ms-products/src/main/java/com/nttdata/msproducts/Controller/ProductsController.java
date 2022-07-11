package com.nttdata.msproducts.Controller;

import com.nttdata.msproducts.model.Products;
import com.nttdata.msproducts.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
public class ProductsController {
    @Autowired
    private ProductsService service;

    @GetMapping("/findAll")
    public Flux<Products> getProducts(){
        return service.findAll();
    }

    @GetMapping("/find/{id}")
    public Mono<Products> getProducts(@PathVariable String id){
        Mono<Products> newProducts = service.findById(id);
        return newProducts;
    }

    @PostMapping("/create")
    public Mono<Products> createProducts(@RequestBody Products c){
        Mono<Products> newProducts = service.save(c);
        return newProducts;
    }

    @PutMapping("/update/{id}")
    public Mono<Products> updateProducts(@RequestBody Products c, @PathVariable String id){
        return service.update(c,id);
    }

    @DeleteMapping("/delete/{id}")
    public Mono<Products> deleteProducts(@PathVariable String id){
        return service.delete(id);
    }

}
