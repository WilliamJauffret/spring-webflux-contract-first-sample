package fr.william.jauffret.springwebfluxcontractfirstsample.controller;

import fr.william.jauffret.springreactiveopenapicodegen.api.v1.PetApi;
import fr.william.jauffret.springreactiveopenapicodegen.dto.PetDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Validated
@RequestMapping("/api/v1")
public class PetController implements PetApi {
    @Override
    public Mono<ResponseEntity<Flux<PetDto>>> getPets(ServerWebExchange exchange) {
        return PetApi.super.getPets(exchange);
    }
}
