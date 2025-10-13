package tacos.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.crypto.password.PasswordEncoder;
import tacos.data.jpa.UserRepositoryJPA;
import tacos.security.RegistrationForm;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private UserRepositoryJPA userRepo;
    private PasswordEncoder encoder;

    public RegistrationController(UserRepositoryJPA userRepo, PasswordEncoder encoder) {
        this.encoder = encoder;
        this.userRepo = userRepo;
    }
    
    @GetMapping
    public String registerForm() {
        return "registration";
    }

    @PostMapping
    public String processRegistration(RegistrationForm form) {
        userRepo.save(form.toUser(encoder));
        return "redirect:/login";
    }

}
