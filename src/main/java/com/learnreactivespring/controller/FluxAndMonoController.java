package com.learnreactivespring.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
public class FluxAndMonoController {

    @GetMapping("/getIntegers")
    public Flux<Integer> getIntegers() {
        return Flux.just(1, 2, 3, 4)
                .delayElements(Duration.ofSeconds(1))
                .log();
    }

    @GetMapping(value = "/getLongStream", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Long> getLongStream() {
        return Flux.interval(Duration.ofSeconds(1))
                .log();
    }

    @GetMapping("/getInteger")
    public Mono<Integer> getInteger() {
        return Mono.just(1)
                .log();
    }
}
