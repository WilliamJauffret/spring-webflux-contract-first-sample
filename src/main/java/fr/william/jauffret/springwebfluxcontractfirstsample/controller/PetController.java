package fr.william.jauffret.springwebfluxcontractfirstsample.controller;

import fr.william.jauffret.springwebfluxcontractfirstsample.api.v1.PetApi;
import fr.william.jauffret.springwebfluxcontractfirstsample.dto.PetDto;
import jdk.jfr.StackTrace;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;
import java.util.stream.Stream;

@RestController
@Validated
@RequestMapping("/api/v1")
public class PetController implements PetApi {

    @Override
    public Mono<ResponseEntity<Flux<PetDto>>> getPets(ServerWebExchange exchange) {
        return Mono.empty();
    }

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseEntity<Void>> addPet(Mono<PetDto> petDto, ServerWebExchange exchange) {
        return Mono.empty();
    }
}
