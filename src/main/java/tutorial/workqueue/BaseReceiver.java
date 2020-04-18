package tutorial.workqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public abstract class BaseReceiver {

    protected static final String QUEUE_NAME = "workQueue";

    protected static boolean durable = true;

    protected Channel getChannel() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setVirtualHost("/defaultHost");
        factory.setUsername("root");
        factory.setPassword("1");

        return factory.newConnection().createChannel();
    }

    public abstract void consumer() throws Exception;

}
