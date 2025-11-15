package tacos.messages.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import tacos.model.Order;

@Service
public class JmsOrderMessagingService implements OrderMessagingService {

    private JmsTemplate jms;

    @Autowired
    public JmsOrderMessagingService(JmsTemplate jms) {
        this.jms = jms;
    }

    @Override
    public void sendOrder(Order order) {
        jms.convertAndSend("tacocloud.order.queue", order, this::addOrderSource);
    }

    // MessagePostProcessor that adds the X_ORDER_SOURCE property to the message before it is sent to the queue
    private Message addOrderSource(Message message) throws JMSException {
        message.setStringProperty("X_ORDER_SOURCE", "WEB");
        return message;
    }
}
