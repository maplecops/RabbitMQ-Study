package tutorial.rpc;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.nio.charset.StandardCharsets;

public class Service1 {

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(ConnectionUtils.RPC_QUEUE_NAME, false, false, false, null);
        channel.basicQos(1);
        Object monitor = new Object();
        System.out.println(String.format("INIT A Monitor with hashCode [%s]", String.valueOf(monitor.hashCode())));
        channel.basicConsume(ConnectionUtils.RPC_QUEUE_NAME, false, (consumerTag, message) -> {
            //TODO
            String correlationId = message.getProperties().getCorrelationId();
            String strInt = new String(message.getBody(), StandardCharsets.UTF_8);
            String callBackQueue = message.getProperties().getReplyTo();
            System.out.println(String.format("Server1 GOT Message correlationId is [%s], messageBody value is [%s] and callback" +
                    " Queue is [%s]", correlationId, strInt, callBackQueue));
            int i = 0;
            try {
                i = Integer.parseInt(strInt);
            } catch (Exception e) {
                i = 0;
            }
            String returnValue = "";
            AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder().correlationId(correlationId).build();
            try {
                returnValue += fib(i);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    Thread.sleep(10 * 1000L);
                } catch (InterruptedException _ignore) {
                }
                channel.basicPublish("", callBackQueue, properties, returnValue.getBytes(StandardCharsets.UTF_8));
                channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
                System.out.println(String.format("Server1 return correlationId [%s] value is [%s]", correlationId, returnValue));
                synchronized (monitor) {
                    System.out.println("handle out! notify stop waiting...");
                    monitor.notify();
                }
            }
        }, consumerTag -> {
            // nothing to do
        });
        while (true) {
            synchronized (monitor) {
                System.out.println("monitor start waiting...");
                monitor.wait();
                System.out.println("monitor end waiting...");
            }
        }
    }

    private static int fib(int i) {
        if (i == 0) {
            return 0;
        }
        if (i == 1) {
            return 1;
        }
        return fib(i - 1) + fib(i - 2);
    }

}
