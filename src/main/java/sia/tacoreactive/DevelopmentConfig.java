package sia.tacoreactive;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;
import sia.tacoreactive.entity.Ingredient;
import sia.tacoreactive.entity.Taco;
import sia.tacoreactive.repository.IngredientRepository;
import sia.tacoreactive.repository.TacoRepository;

@Slf4j
@Configuration
public class DevelopmentConfig {
    
    @Bean
    public CommandLineRunner dataLoader(TacoRepository tacoRepo, IngredientRepository ingredientRepository){
        return args -> {
            List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP),
                new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP),
                new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN),
                new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN),
                new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES),
                new Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES),
                new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE),
                new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE),
                new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE),
                new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE),
                new Ingredient("FISH", "Stinky Fish", Ingredient.Type.PROTEIN)
            );

            ingredientRepository.saveAll(ingredients);

            //log.info("Ingredients:");

            //ingredientRepository.findAll().subscribe(ingredient -> log.info(ingredient.toString()));

            /* 
            Taco taco1 = new Taco();
            taco1.setName("Beef Taco");
            taco1.setIngredients(new LinkedHashSet<>(Arrays.asList(ingredients.get(0).getId(), ingredients.get(2).getId(), ingredients.get(4).getId())));

            Taco taco2 = new Taco();
            taco2.setName("Carnitas Taco");
            taco2.setIngredients(new LinkedHashSet<>(Arrays.asList(ingredients.get(1).getId(), ingredients.get(3).getId(), ingredients.get(5).getId())));

            Taco taco3 = new Taco();
            taco3.setName("Fish Taco");
            taco3.setIngredients(new LinkedHashSet<>(Arrays.asList(ingredients.get(8).getId(), ingredients.get(6).getId(), ingredients.get(10).getId())));

            tacoRepo.save(taco1);
            tacoRepo.save(taco2);
            tacoRepo.save(taco3);
            */
    
        };
    }

}
