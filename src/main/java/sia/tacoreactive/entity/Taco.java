package sia.tacoreactive.entity;

import java.time.LocalDateTime;
//import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;


@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Taco {

    @Id
    private Long id;

    private LocalDateTime createdAt = LocalDateTime.now();

    @NonNull
    @Size(min = 4, message="Name should be 4 characters or long")
    private String name;

    @Size(min = 1, message = "Choose 1 or more ingredients")
    private Set<Long> ingredientIds = new LinkedHashSet<>();

    public void addIngredient(Ingredient ingredient){
        ingredientIds.add(ingredient.getId());
    }

    
}
