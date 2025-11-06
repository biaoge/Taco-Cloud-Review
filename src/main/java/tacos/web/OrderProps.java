package tacos.web;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Validated
@Component
@Data
@ConfigurationProperties(prefix = "tacos.orders")
public class OrderProps {

    @Min(value = 5, message = "must be between 5 and 20")
    @Max(value = 20, message = "must be between 5 and 20")
    private int pageSize = 20;
    
}
