package tacos.web;

import org.springframework.stereotype.Component;

import tacos.data.IngredientRepository;
import tacos.data.jpa.IngredientRepositoryJPA;
import tacos.model.Ingredient;

/*
 * A converter to convert ingredient IDs (Strings) to Ingredient objects.
 * This is used to automatically convert form parameters to model attributes.
 * For example, when a form submits a list of ingredient IDs, this converter will
 * convert those IDs to Ingredient objects using the IngredientRepository.
 */
@Component
public class IngredientByIdConverter implements org.springframework.core.convert.converter.Converter<String, Ingredient> {

    // changed from OrderRepository to OrderRepositoryJPA to use JPA implementation rather than JDBC implementation
    private IngredientRepositoryJPA ingredientRepo;

    // Constructor injection for the IngredientRepository
    public IngredientByIdConverter(IngredientRepositoryJPA ingredientRepo) {
        this.ingredientRepo = ingredientRepo;
    }

    @Override
    public Ingredient convert(String id) {
        return ingredientRepo.findById(id).orElse(null);
    }
    
}
