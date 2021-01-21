package com.learnreactivespring.fluxandmono;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

public class FluxAndMonoFilterTest {

    List<String> names = Arrays.asList("Suman", "Monalisha", "Unmisha", "Ushnik");

    @Test
    public void filterTest(){
        Flux<String> stringFlux = Flux.fromIterable(names)
                .filter(s -> s.toLowerCase().startsWith("u"))
                .log();

        StepVerifier.create(stringFlux)
                .expectNext("Unmisha", "Ushnik")
                .verifyComplete();
    }
}
