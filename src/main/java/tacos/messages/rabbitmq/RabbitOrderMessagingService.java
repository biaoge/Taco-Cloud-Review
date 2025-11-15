package tacos.messages.rabbitmq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;

import tacos.messages.jms.OrderMessagingService;
import tacos.model.Order;

public class RabbitOrderMessagingService implements OrderMessagingService {

    private RabbitTemplate rabbit;

    @Autowired
    public RabbitOrderMessagingService(RabbitTemplate rabbit) {
        this.rabbit = rabbit;
    }

    /**
     * We could also use convertAndSend method instead of sending the message manually. But given convertAndSend
     * is already used in the JmsOrderMessagingService, we use the manual approach here to demonstrate the use of raw send() of the RabbitTemplate.
     */
    @Override
    public void sendOrder(Order order) {
        MessageConverter converter = rabbit.getMessageConverter();
        MessageProperties props = new MessageProperties();
        props.setHeader("X_ORDER_SOURCE", "WEB");
        Message message = converter.toMessage(order, props);
        rabbit.send("tacocloud.order.queue", message);
    }
}
