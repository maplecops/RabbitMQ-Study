package tutorial.routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

import java.io.IOException;

public class Subscriber {

    public static void main(String[] args) throws IOException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        String queue = channel.queueDeclare().getQueue();
        channel.queueBind(queue, ConnectionUtils.EXCHANGE_NAME, "0");

        DeliverCallback callback = new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery message) throws IOException {
                String str = new String(message.getBody(), "UTF-8");
                System.out.println("log1 got : " + str);
            }
        };
        channel.basicConsume(queue, true, callback, _1 -> {
        });
    }

}
