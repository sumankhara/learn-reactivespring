package com.learnreactivespring.fluxandmono;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxAndMonoErrorTest {

    @Test
    public void fluxErrorHandling_onErrorResume() {
        Flux<String> stringFlux = Flux.just("A", "B", "C")
                .concatWith(Flux.error(new RuntimeException("Error occurred")))
                .concatWith(Flux.just("D"))
                .onErrorResume((exp) -> {
                    System.out.println("Exception is: " + exp.getMessage());
                    return Flux.just("onErrorValue1", "onErrorValue2");
                });

        StepVerifier.create(stringFlux.log())
                .expectNext("A", "B", "C")
                .expectNext("onErrorValue1", "onErrorValue2")
                .verifyComplete();
    }

    @Test
    public void fluxErrorHandling_onErrorReturn() {
        Flux<String> stringFlux = Flux.just("A", "B", "C")
                .concatWith(Flux.error(new RuntimeException("Error occurred")))
                .concatWith(Flux.just("D"))
                .onErrorReturn("onErrorValue");

        StepVerifier.create(stringFlux.log())
                .expectNext("A", "B", "C")
                .expectNext("onErrorValue")
                .verifyComplete();
    }

    @Test
    public void fluxErrorHandling_onErrorMap() {
        Flux<String> stringFlux = Flux.just("A", "B", "C")
                .concatWith(Flux.error(new RuntimeException("Error occurred")))
                .concatWith(Flux.just("D"))
                .onErrorMap((ex) -> new CustomException(ex));

        StepVerifier.create(stringFlux.log())
                .expectNext("A", "B", "C")
                .expectError(CustomException.class)
                .verify();
    }

    @Test
    public void fluxErrorHandling_onErrorMap_withRetry() {
        Flux<String> stringFlux = Flux.just("A", "B", "C")
                .concatWith(Flux.error(new RuntimeException("Error occurred")))
                .concatWith(Flux.just("D"))
                .onErrorMap((ex) -> new CustomException(ex))
                .retry(2);

        StepVerifier.create(stringFlux.log())
                .expectNext("A", "B", "C")
                .expectNext("A", "B", "C")
                .expectNext("A", "B", "C")
                .expectError(CustomException.class)
                .verify();
    }
}
