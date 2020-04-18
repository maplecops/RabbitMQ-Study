package tutorial.topics;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.nio.charset.StandardCharsets;

public class Publisher {

    public static void main(String[] args) throws Exception {

        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(ConnectionUtils.TOPIC_EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        channel.basicPublish(ConnectionUtils.TOPIC_EXCHANGE_NAME, "book",
                null, "This is a book.".getBytes(StandardCharsets.UTF_8));
        channel.basicPublish(ConnectionUtils.TOPIC_EXCHANGE_NAME, "apple",
                null, "This is a apple.".getBytes(StandardCharsets.UTF_8));
        channel.basicPublish(ConnectionUtils.TOPIC_EXCHANGE_NAME, "banana",
                null, "This is a banana.".getBytes(StandardCharsets.UTF_8));
        channel.basicPublish(ConnectionUtils.TOPIC_EXCHANGE_NAME, "pear.yellow",
                null, "This is a pear-yellow.".getBytes(StandardCharsets.UTF_8));
    }

}
