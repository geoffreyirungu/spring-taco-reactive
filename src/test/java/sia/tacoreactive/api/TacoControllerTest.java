package sia.tacoreactive.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sia.tacoreactive.entity.Ingredient;
import sia.tacoreactive.entity.Taco;
import sia.tacoreactive.repository.TacoRepository;

class TacoControllerTest {
    @Test
    void shouldReturnRecentTacos() {
        Taco tacos [] = {testTaco(1L),testTaco(2L),testTaco(3L),testTaco(4L)};
        Flux<Taco> tacoFlux = Flux.just(tacos); 

        TacoRepository tacoRepository = Mockito.mock(TacoRepository.class);
        when(tacoRepository.findAll()).thenReturn(tacoFlux);

        WebTestClient testClient = WebTestClient.bindToRouterFunction(new TacoController(tacoRepository).routerFunction()).build();

        testClient.get().uri("/api/tacos?recent")
            .exchange()
            .expectStatus().isOk()
            .expectBody()   
                .jsonPath("$").isArray()
                .jsonPath("$").isNotEmpty()
                .jsonPath("$[0].id").isEqualTo(tacos[0].getId().toString())
                .jsonPath("$[0].name").isEqualTo(tacos[0].getName().toString())
                .jsonPath("$[3]").doesNotExist();

    }

    @Test
    void shouldSaveATaco(){
        TacoRepository tacoRepository = Mockito.mock(TacoRepository.class);

        WebTestClient testClient = WebTestClient.bindToRouterFunction(new TacoController(tacoRepository).routerFunction()).build();

        Mono<Taco> unsavedTacoMono = Mono.just(testTaco(0L));
        Taco savedTaco = testTaco(1L);

        when(tacoRepository.save(any(Taco.class))).thenReturn(Mono.just(savedTaco));

        testClient.post().uri("/api/tacos")
            .contentType(MediaType.APPLICATION_JSON)
            .body(unsavedTacoMono,Taco.class)
            .exchange()
            .expectStatus().isCreated()
            .expectBody(Taco.class)
            .isEqualTo(savedTaco);
    }

    private Taco testTaco(Long number){
        Taco taco = new Taco();
        taco.setId(number != null ? number : 1);
        taco.setName("Taco "+number);
        Set<Long> ingredients = new LinkedHashSet<>();
        ingredients.add(new Ingredient(1L,"INGA", "Ingredient A", Ingredient.Type.CHEESE).getId());
        ingredients.add(new Ingredient(2L,"INGA", "Ingredient A", Ingredient.Type.CHEESE).getId());
        taco.setIngredientIds(ingredients);
        return taco;
    }
}
