package tacos.web.api.resources;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import tacos.model.Ingredient;
import tacos.model.Ingredient.Type;

public class IngredientResource extends RepresentationModel<IngredientResource> {

    @Getter
    private final String name;

    @Getter
    private final Type type;

    public IngredientResource(Ingredient ingredient) {
        this.name = ingredient.getName();
        this.type = ingredient.getType();
    }
}
