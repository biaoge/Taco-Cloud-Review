package tacos;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import tacos.data.jpa.IngredientRepositoryJPA;
import tacos.data.jpa.OrderRepositoryJPA;
import tacos.data.jpa.TacoRepositoryJPA;
import tacos.data.jpa.UserRepositoryJPA;
import tacos.web.OrderProps;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest
@AutoConfigureMockMvc(addFilters = false)
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IngredientRepositoryJPA ingredientRepository;

    @MockBean
    private TacoRepositoryJPA tacoRepository;

    @MockBean
    private OrderRepositoryJPA orderRepository;

    @MockBean
    private UserRepositoryJPA userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private OrderProps orderProps;

    @MockBean
    private EntityLinks entityLinks;

    @Test
    public void testHomePage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(content().string(containsString("Welcome to Taco Cloud!")));
    }
    

}
