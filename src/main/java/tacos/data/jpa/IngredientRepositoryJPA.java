package tacos.data.jpa;

import org.springframework.data.repository.CrudRepository;

import tacos.model.Ingredient;

/**
 * 
 * The good news about Spring Data JPA is that you don't need to write any implementation code for this interface.
 * This will be AUTO IMPLEMENTED by Spring into a Bean called ingredientRepositoryJPA
 * 
 * Just inject IngredientRepositoryJPA into any other Spring-managed component and you can start using it right away.
*/
public interface IngredientRepositoryJPA extends CrudRepository<Ingredient, String> {
    
}
