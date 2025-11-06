package tacos.web.api.resources;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import tacos.model.Taco;
import tacos.web.api.assembler.IngredientResourceAssembler;

@Relation(value = "taco", collectionRelation = "tacos")
public class TacoResource extends RepresentationModel<TacoResource> {

    private final static IngredientResourceAssembler ingredientAssembler = new IngredientResourceAssembler();
    
    @Getter
    private final String name;
    
    @Getter
    private final Date createdAt;

    @Getter
    private final List<IngredientResource> ingredients;

    public TacoResource(Taco taco) {
        this.name = taco.getName();
        this.createdAt = taco.getCreatedAt();
        CollectionModel<IngredientResource> ingredientResources = ingredientAssembler.toCollectionModel(taco.getIngredients());
        this.ingredients = new ArrayList<>(ingredientResources.getContent());
    }

}
