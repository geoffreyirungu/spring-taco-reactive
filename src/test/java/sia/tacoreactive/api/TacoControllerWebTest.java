package sia.tacoreactive.api;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import sia.tacoreactive.entity.Ingredient;
import sia.tacoreactive.entity.Taco;
import sia.tacoreactive.repository.TacoRepository;

import org.springframework.security.test.context.support.WithMockUser;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class TacoControllerWebTest {
    
    @Autowired
    WebTestClient webTestClient;

    @Autowired
    TacoRepository tacoRepo;

    Taco tacos [] = {testTaco(1L),testTaco(2L),testTaco(3L),testTaco(4L)};

    @BeforeEach
    public void setup() {
        
        Flux<Taco> deleteAndInsert = tacoRepo.deleteAll()
            .thenMany(tacoRepo.saveAll(
                Flux.just(
                    tacos
            )));
        
        StepVerifier.create(deleteAndInsert)
                    .expectNextCount(4)
                    .verifyComplete();
    }

    @Test
    @WithMockUser(username = "user", authorities = {"USER"})
    void shouldReturnRecentTacos(){
        webTestClient.get().uri("/api/tacos?recent")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBody()
                .jsonPath("$").isArray()
                .jsonPath("$").isNotEmpty()
                .jsonPath("$[0].id").isEqualTo(tacos[0].getId().toString())
                .jsonPath("$[0].name").isEqualTo(tacos[0].getName().toString())
                .jsonPath("$[3]").doesNotExist();
    }

    private Taco testTaco(Long number){
        Taco taco = new Taco();
        taco.setName("Taco "+number);
        Set<Long> ingredients = new LinkedHashSet<>();
        ingredients.add(new Ingredient(1L,"INGA", "Ingredient A", Ingredient.Type.CHEESE).getId());
        ingredients.add(new Ingredient(2L,"INGA", "Ingredient A", Ingredient.Type.CHEESE).getId());
        taco.setIngredientIds(ingredients);
        return taco;
    }

}
