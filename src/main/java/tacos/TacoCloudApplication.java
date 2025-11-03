package tacos;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import tacos.data.jpa.IngredientRepositoryJPA;
import tacos.data.jpa.UserRepositoryJPA;
import tacos.model.Ingredient;
import tacos.model.Ingredient.Type;
import tacos.model.User;

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
	@Profile("!prod")
	public CommandLineRunner dataLoader(IngredientRepositoryJPA repo,
	UserRepositoryJPA userRepo, PasswordEncoder encoder) {
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
			
				userRepo.save(new User("habuma", encoder.encode("password"),
						"Craig Walls", "123 North Street", "Cross Roads", "TX",
						"76227", "123-123-1234"));
			}
		};
	}

}
