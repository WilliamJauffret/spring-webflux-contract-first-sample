package fr.william.jauffret.springwebfluxcontractfirstsample.controller;

import fr.william.jauffret.springwebfluxcontractfirstsample.api.v1.PetApi;
import fr.william.jauffret.springwebfluxcontractfirstsample.dto.PetDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
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
        PetDto petDto1 = new PetDto();
        petDto1.setPetId(1);
        petDto1.setName("William");
        petDto1.setBirthDate(OffsetDateTime.now());
        Flux<PetDto> flux = Flux.fromStream(Stream.of(petDto1, petDto1, petDto1));
        return Mono.just(ResponseEntity.ok(flux));
    }
}
