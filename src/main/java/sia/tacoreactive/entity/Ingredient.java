package sia.tacoreactive.entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
//@Table("ingredient")
@EqualsAndHashCode(exclude = "id")
public class Ingredient {

    @Id
    private Long id;

    @NonNull
    private String slug;

    @NonNull
    private String name;

    @NonNull
    private Type type;

    public enum Type{
        WRAP,PROTEIN,VEGGIES,CHEESE,SAUCE
    }
}
