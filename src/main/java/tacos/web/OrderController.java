package tacos.web;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import tacos.data.OrderRepository;
import tacos.data.jpa.OrderRepositoryJPA;
import tacos.model.Order;
import tacos.model.User;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order") // This indicates that the "order" attribute should be kept in the session across multiple requests.
public class OrderController {


    // changed from OrderRepository to OrderRepositoryJPA to use JPA implementation rather than JDBC implementation
    private OrderRepositoryJPA orderRepo;

    private OrderProps orderProps;

    public OrderController(OrderRepositoryJPA orderRepo, OrderProps orderProps) {
        this.orderRepo = orderRepo;
        this.orderProps = orderProps;
    }

    @GetMapping("/current")
    public String orderForm(Model model) {
        // model.addAttribute("order", new Order());
        return "orderForm"; 
    }

    @PostMapping
    public String processOrder(
        @Valid Order order, 
        Errors errors, 
        SessionStatus sessionStatus,
        @AuthenticationPrincipal User user) {
        if (errors.hasErrors()) {
            return "orderForm"; // Return to the order form if there are validation errors
        }

        order.setUser(user);
        orderRepo.save(order); // Save the order using the OrderRepository
        sessionStatus.setComplete(); // Mark the session as complete to remove the "order" attribute from the session

        // In a real application, you would save the order to the database here
        log.info("order submitted: " + order);
        return "redirect:/"; // Redirect to home page after processing the order
    }

    @GetMapping
    public String ordersForUser(
        @AuthenticationPrincipal User user,
        Model model) {
        
            Pageable pageable = PageRequest.of(0, orderProps.getPageSize());
            model.addAttribute("orders", orderRepo.findByUserOrderByPlacedAtDesc(user, pageable));
        
        return "orderList";
    }
}
