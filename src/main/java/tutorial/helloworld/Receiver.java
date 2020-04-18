package tutorial.helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class Receiver {

    private static final String QUEUE_NAME = "hello_world";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("root");
        factory.setPassword("1");
        factory.setVirtualHost("/defaultHost");

        Channel channel = factory.newConnection().createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            String bodyOut = new String(message.getBody());
            System.out.println(String.format("Receive Message is [%s]", bodyOut));
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
        });
    }

}
