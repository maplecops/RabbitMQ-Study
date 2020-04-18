package tutorial.workqueue.rece;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import tutorial.workqueue.BaseReceiver;

public class Receiver2 extends BaseReceiver {

    public static void main(String[] args) throws Exception {
        Receiver2 _2 = new Receiver2();
        _2.consumer();
    }

    @Override
    public void consumer() throws Exception {
        Channel channel = getChannel();

        channel.basicQos(1);

        channel.queueDeclare(QUEUE_NAME, durable, false, false, null);

        DeliverCallback deliverCallback = (consumerTag, message) -> {
            try {
                String str = new String(message.getBody(), "UTF-8");
                System.out.println("2@" + str);
                Thread.sleep(Integer.parseInt(str) * 100);
                System.out.println("2#" + str);
            } catch (Exception e) {
                e.printStackTrace();
            }
            channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
        };

        channel.basicConsume(QUEUE_NAME, false, deliverCallback, cancelCallback -> {
        });
    }
}
