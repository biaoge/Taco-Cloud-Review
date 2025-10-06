package tacos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.AccessLevel;

@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true) // Private no-args constructor for JPA
@Entity
/**
 * Explanation of why we have both @RequiredArgsConstructor and @NoArgsConstructor:

The Ingredient class uses both @RequiredArgsConstructor and @NoArgsConstructor because it needs to satisfy the requirements of two different software components: Lombok (for ease of development) and the Jakarta Persistence API (JPA) (for database operations). 
The purpose of each annotation
@RequiredArgsConstructor
Generates a constructor with arguments for all final fields in the class.
Allows for creating new objects in your code, like new Ingredient("FLTO", "Flour Tortilla", WRAP).
Promotes immutability, a good practice for data-carrying objects. By using final fields, you ensure that an Ingredient object's state cannot change after it is created. 

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true) 
Generates a constructor with no arguments. This is a mandatory requirement of the JPA specification for all entity classes. JPA implementations, such as Hibernate, use this constructor with Java Reflection to create instances of the entity when loading data from the database.
Makes the no-argument constructor private (access = AccessLevel.PRIVATE). This prevents your application code from accidentally creating an uninitialized Ingredient object. You still want to enforce the use of the all-args constructor you intended for your application logic.
Forces the generation of the no-argument constructor (force = true), even though the class has final fields. By default, Lombok will refuse to generate a no-args constructor for a class with final fields, as those fields would be uninitialized. The force = true argument overrides this behavior by initializing all final fields to their default values (null in this case). 

Why you need both
You need both annotations to make the Ingredient class a robust and fully-functional JPA entity: 
For your code to create new objects, you use the final fields and the @RequiredArgsConstructor-generated constructor to ensure every Ingredient object is created with all its essential data. This promotes reliable, immutable object creation.
For the JPA framework to function, it must be able to load existing objects from the database, which requires a no-argument constructor. The @NoArgsConstructor fulfills this technical requirement for JPA, but the access level is restricted to protect your domain model from being misused. 
 */
// Here the data will be persisted in the "Ingredient" table
public class Ingredient {

    @Id
    private final String id;
    private final String name;
    private final Type type;

    public static enum Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }
}
