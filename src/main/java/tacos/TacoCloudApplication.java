package tacos;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import tacos.data.jpa.IngredientRepositoryJPA;
import tacos.model.Ingredient;
import tacos.model.Ingredient.Type;

// This annatation is a combination of @Configuration, @EnableAutoConfiguration, and @ComponentScan
// With it, this class acts as the main configuration class for the Spring Boot application
@SpringBootApplication
public class TacoCloudApplication {

	public static void main(String[] args) {
		/*
		with this line, Spring Boot will start the application, create the Spring application context, and perform a class path scan
		More details:
		- Bootstraps the application: Starts the Spring application context.
		- Performs classpath scanning: Finds and registers beans/components annotated with @Component, @Service, @Controller, etc. 
		  Applies auto-configuration: Sets up beans and configuration based on the dependencies in your project.
		- Starts the embedded server: (like Tomcat) so your app can handle web requests.
		- Processes command-line arguments: Passes args to the application. 
		*/ 
		SpringApplication.run(TacoCloudApplication.class, args);
	}

	/**
	 * It seems after switching to JPA, the data.sql file is not being executed automatically.
	 * To ensure the database is populated with initial data, we can use a CommandLineRunner
	 * @param repo
	 * @return
	 * 
	 * This approach is guaranteed to work because it uses your Spring Data JPA repository directly to save the data after the application context (and thus the database connection and schema) is fully set up
	 */
	@Bean
	public CommandLineRunner dataLoader(IngredientRepositoryJPA repo) {
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
				repo.save(new Ingredient("FLTO", "Flour Tortilla", Type.WRAP));
				repo.save(new Ingredient("COTO", "Corn Tortilla", Type.WRAP));
				repo.save(new Ingredient("GRBF", "Ground Beef", Type.PROTEIN));
				repo.save(new Ingredient("CARN", "Carnitas", Type.PROTEIN));
				repo.save(new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES));
				repo.save(new Ingredient("LETC", "Lettuce", Type.VEGGIES));
				repo.save(new Ingredient("CHED", "Cheddar", Type.CHEESE));
				repo.save(new Ingredient("JACK", "Monterrey Jack", Type.CHEESE));
				repo.save(new Ingredient("SLSA", "Salsa", Type.SAUCE));
				repo.save(new Ingredient("SRCR", "Sour Cream", Type.SAUCE));
			}
		};
	}

}
