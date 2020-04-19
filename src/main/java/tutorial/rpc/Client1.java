package tutorial.rpc;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Client1 {

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(ConnectionUtils.RPC_QUEUE_NAME, false, false, false, null);
        String replyQueue = channel.queueDeclare().getQueue();
        String uuid = UUID.randomUUID().toString();


        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                .correlationId(uuid)
                .replyTo(replyQueue)
                .build();
        String message = "4";
        channel.basicPublish("", ConnectionUtils.RPC_QUEUE_NAME, properties, message.getBytes(StandardCharsets.UTF_8));
        System.out.println(String.format("client1 send message with correlationId [%s] , replyQueue [%s] , message is [%s]",
                uuid, replyQueue, message));
        System.out.println("client1 waiting for response...");
        final BlockingQueue<String> queue = new ArrayBlockingQueue(1);
        String tag = channel.basicConsume(replyQueue, true, (consumerTag, message1) -> {
            if (message1.getProperties().getCorrelationId().equals(uuid)) {
                queue.offer(new String(message1.getBody(), StandardCharsets.UTF_8));
            }
        }, consumerTag -> {
            // do nothing
        });
        String result = queue.take();
        System.out.println(String.format("client1 get response [%s]", result));
        channel.basicCancel(tag);
    }

}
