package tutorial.workqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Sender {

    private static final String QUEUE_NAME = "workQueue";

    private static boolean durable = true;

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setVirtualHost("/defaultHost");
        factory.setUsername("root");
        factory.setPassword("1");

        Channel channel = factory.newConnection().createChannel();

        channel.queueDeclare(QUEUE_NAME, durable, false, false, null);

        for (int i = 0; i < 100; i++) {
            channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, String.valueOf(i).getBytes());
        }
        System.out.println("Finished Publish Message...");
    }

}
