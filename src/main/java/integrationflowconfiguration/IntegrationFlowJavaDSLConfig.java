package integrationflowconfiguration;

import java.io.File;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.support.FileExistsMode;

@Configuration
public class IntegrationFlowJavaDSLConfig {

    @Bean
    public IntegrationFlow fileWritingFlow() {
        return IntegrationFlow
            .from("textInChannel")
            .transform(String.class, String::toUpperCase)
            .handle(Files.outboundAdapter(new File("src/main/resources/data/output"))
                .fileExistsMode(FileExistsMode.APPEND)
                .appendNewLine(true))
            .get();
    }
}
