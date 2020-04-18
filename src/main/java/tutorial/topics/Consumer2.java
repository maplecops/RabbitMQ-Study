package tutorial.topics;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.nio.charset.StandardCharsets;

/**
 * @author maplecops
 */
public class Consumer2 {

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();

        String queue = channel.queueDeclare().getQueue();
        channel.queueBind(queue, ConnectionUtils.TOPIC_EXCHANGE_NAME, "pear.#");

        channel.basicConsume(queue, true, (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(String.format("Consumer2 get message [%s]", message));
        }, consumerTag -> {
            //nothing to do.
        });
    }

}
