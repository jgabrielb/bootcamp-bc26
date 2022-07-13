package com.nttdata.msdeposits.client;

import com.nttdata.msdeposits.model.Product;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ProductClient {
    private WebClient client = WebClient.create("http://localhost:9003/products");

    public Mono<Product> getProduct(String id){
        return client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/find/{id}")
                        .build(id)
                )
                .retrieve()
                .bodyToMono(Product.class);
    };
}
