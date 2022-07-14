package com.nttdata.msaccounts.client;

import com.nttdata.msaccounts.model.Deposit;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class DepositClient {
    private WebClient client = WebClient.create("http://localhost:9006/deposits");

    public Mono<Deposit> getDeposit(String id){
        return client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/find/{id}")
                        .build(id)
                )
                .retrieve()
                .bodyToMono(Deposit.class);
    };
}
