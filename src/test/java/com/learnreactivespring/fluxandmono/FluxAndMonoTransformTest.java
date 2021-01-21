package com.learnreactivespring.fluxandmono;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

public class FluxAndMonoTransformTest {

    List<String> names = Arrays.asList("Suman", "Monalisha", "Unmisha", "Ushnik");

    @Test
    public void transformUsingMap() {
        Flux<String> namesFlux = Flux.fromIterable(names)
                .map(s -> s.toUpperCase())
                .log();

        StepVerifier.create(namesFlux)
                .expectNext("Suman".toUpperCase(), "Monalisha".toUpperCase(), "Unmisha".toUpperCase(), "Ushnik".toUpperCase())
                .verifyComplete();
    }

    @Test
    public void transformUsingMap_repeat() {
        Flux<Integer> integerFlux = Flux.fromIterable(names)
                .map(String::length)
                .repeat(1)
                .log();

        StepVerifier.create(integerFlux)
                .expectNext(5, 9, 7, 6, 5, 9, 7, 6)
                .verifyComplete();
    }

    @Test
    public void transformUsingFlatMap() {
        Flux<String> stringFlux = Flux.fromIterable(Arrays.asList("a", "b", "c", "d", "e", "f"))
                .flatMap(s -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return Flux.fromIterable(Arrays.asList(s, "newValue"));
                }).log();

        StepVerifier.create(stringFlux)
                .expectNextCount(12)
                .verifyComplete();
    }
}
