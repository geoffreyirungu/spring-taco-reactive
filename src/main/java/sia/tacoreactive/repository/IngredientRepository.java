package sia.tacoreactive.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import reactor.core.publisher.Mono;
import sia.tacoreactive.entity.Ingredient;


public interface IngredientRepository extends ReactiveCrudRepository<Ingredient,Long>{
    Mono<Ingredient> findBySlug(String slug);
}
