package integrationflowconfiguration;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.integration.file.FileHeaders;

@MessagingGateway(defaultRequestChannel = "textInChannel")
public interface FileWriterGateway {

    void writeToFile(@Header(FileHeaders.FILENAME) String filename, String data);
}