package sia.tacoreactive.entity;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class TacoIngredient {
    
    @Id
    private long id;

    private long tacoId;

    private String ingredientId;

}
