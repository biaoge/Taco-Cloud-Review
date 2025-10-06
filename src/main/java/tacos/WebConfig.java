package tacos;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // this maps the root URL ("/") to the "home" view without needing a dedicated controller, this replace the HomeController
        registry.addViewController("/").setViewName("home");
    }

}
