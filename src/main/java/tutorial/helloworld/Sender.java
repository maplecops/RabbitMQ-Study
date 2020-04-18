package tutorial.helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Sender {

    private static final String QUEUE_NAME = "hello_world";

    public static void main(String[] args) {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("root");
        factory.setPassword("1");
        factory.setVirtualHost("/defaultHost");
        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            String message = "Hello World";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());

            System.out.println("finish send message");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
