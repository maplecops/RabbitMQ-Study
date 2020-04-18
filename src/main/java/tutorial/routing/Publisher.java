package tutorial.routing;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

public class Publisher {

    public static void main(String[] args) throws IOException, InterruptedException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(ConnectionUtils.EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        int i = 0;
        while (true) {
            String message = "message_" + i++;
            System.out.println(message);
            channel.basicPublish(ConnectionUtils.EXCHANGE_NAME, String.valueOf(i % 3), null, message.getBytes("UTF-8"));
            Thread.sleep(500);
        }
    }

}
