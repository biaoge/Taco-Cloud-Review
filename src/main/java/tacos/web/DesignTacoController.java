package tacos.web;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import tacos.data.IngredientRepository;
import tacos.data.TacoRepository;
import tacos.data.jpa.IngredientRepositoryJPA;
import tacos.data.jpa.TacoRepositoryJPA;
import tacos.model.Ingredient;
import tacos.model.Order;
import tacos.model.Taco;
import tacos.model.Ingredient.Type;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;



@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order") // Indicate that the "order" attribute should be kept in the session across multiple requests.
public class DesignTacoController {

    // changed from OrderRepository to OrderRepositoryJPA to use JPA implementation rather than JDBC implementation
    private final IngredientRepositoryJPA ingredientRepo;
    private final TacoRepositoryJPA tacoDesignRepo;

    @Autowired
    public DesignTacoController(
        IngredientRepositoryJPA ingredientRepo,
        TacoRepositoryJPA tacoRepo) {
        this.ingredientRepo = ingredientRepo;
        this.tacoDesignRepo = tacoRepo;
    }

    @ModelAttribute(name = "order")
    public Order order() {
        return new Order();
    }

    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }


    @GetMapping
    public String showDesignForm(Model model) {
        populateIngredientsTomodel(model);
        
        // This means the form-backing object in the model is named "taco".
        model.addAttribute("taco", new Taco());
        
        // This is still a GET request, so just return the "design" view to show the form.
        return "design";
    }

    @PostMapping()
    /**
     * 
     * @param taco Keep the Taco argument for automatic binding and validation (Spring will look for a model attribute named "taco" to bind the form data.).
     * Do not try to retrieve the form object from the model in a POST handler(e.g., model.getAttribute("taco")).
     * The model does not automatically contain a populated Taco object from the form submission unless you declare it as a method parameter.
     * 
     * @param errors Errors is used to check for validation errors after binding the form data to the Taco object.
     * @param model The Model is for adding extra data for the view (The Model parameter allows you to add other attributes (like your ingredient lists) for rendering the view.), not for receiving form submissions.
     * @param order The Order object is retrieved from the session (because of @SessionAttributes) and is used to add the designed taco to the current order. It also tells Spring to not attempt to bind form data to the Order object.
     * 
     * 
     * @return If there are validation errors, return the "design" view to show the form again. Otherwise, process the taco design and redirect to the order page.
     *
     */
    public String procesDesign(@Valid Taco taco, Errors errors, Model model, @ModelAttribute Order order) {
        if (errors.hasErrors()) {
            /*
             * Return to the design view if there are validation errors. This just forward the POST request to the /design URL. 
             * Note: 
             * The Model object in a Spring MVC controller is request-scoped—it is created fresh for each HTTP request.
             *  - Any attributes you add to the Model are available only for the duration of that request (GET or POST).
             *  - If you return a view name (like "design"), the model attributes are passed to the view for rendering.
             *  - If you redirect (like redirect:/design), a new request is made and the model is empty unless you use RedirectAttributes or session attributes.
             * 
             * Now for the POST request, the model doesn't have any ingredients info available. So if we don't repopulate the model with the ingredients before return, the design view won't have the data it needs to render the form correctly.
             * We also cannot change to "redirect:/design" because that would trigger a new GET request to /design, losing the validation error messages and the user's input.
             */

            populateIngredientsTomodel(model);
            return "design";
        }

        Taco savedTaco = tacoDesignRepo.save(taco);
        order.addDesign(savedTaco);


        log.info("Processing taco design: " + taco);
        // This is a new GET request, not the same POST request.
        return "redirect:/orders/current";
    }
    
    private void populateIngredientsTomodel(Model model) {
        List<Ingredient> ingredientsFromRepo = (List<Ingredient>) ingredientRepo.findAll();

        /*
        List <Ingredient> ingredients = Arrays.asList(
                new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP),
                new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP),
                new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN),
                new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN),
                new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES),
                new Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES),
                new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE),
                new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE),
                new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE),
                new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE)
        );
         */

        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredientsFromRepo, (Ingredient.Type) type));
        }
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Ingredient.Type type) {
        return ingredients
        .stream()
        .filter(x -> x.getType().equals(type))
        .collect(Collectors.toList());
    }
}
