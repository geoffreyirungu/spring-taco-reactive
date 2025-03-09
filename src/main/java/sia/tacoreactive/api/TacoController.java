package sia.tacoreactive.api;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.queryParam;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;


import java.net.URI;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import sia.tacoreactive.entity.Taco;
import sia.tacoreactive.repository.TacoRepository;


@Slf4j
@Configuration
@RequiredArgsConstructor
public class TacoController {
    
    private final TacoRepository tacoRepository;

    @Bean
    public RouterFunction<?> routerFunction(){
        return route(GET("/api/tacos")
                    .and(queryParam("recent", t -> t != null)), this::recent)
                .andRoute(POST("/api/tacos"),this::postTaco);
    }


    private Mono<ServerResponse> recent(ServerRequest request){
        return ServerResponse.ok().body(tacoRepository.findAll().take(3),Taco.class)
                .doOnError(error -> log.info("Error occurred: {}", error.getMessage()));
    }

    private Mono<ServerResponse> postTaco(ServerRequest request){
        return request.bodyToMono(Taco.class)
                .flatMap(tacoRepository::save)
                .flatMap(savedTaco -> ServerResponse.created(URI.create("http://localhost:8080/api/tacos/"+savedTaco.getId()))
                    .body(Mono.just(savedTaco),Taco.class)
                )
                .doOnError(error -> log.info("Error occurred: {}", error.getMessage()));  // Logging 
    }


    /* 
    @GetMapping(params = "recent")
    public Flux<Taco> recentTacos(){
        return tacoRepository.findAll().take(2);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Taco> postTaco(@RequestBody Mono<Taco> taco){
        return taco.flatMap(tacoRepository::save);
    }
    */

}
