package tutorial.workqueue.rece;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import tutorial.workqueue.BaseReceiver;

public class Receiver1 extends BaseReceiver {


    public static void main(String[] args) throws Exception {
        Receiver1 _1 = new Receiver1();
        _1.consumer();
    }

    @Override
    public void consumer() throws Exception {
        Channel channel = getChannel();

        channel.basicQos(1);

        channel.queueDeclare(QUEUE_NAME, durable, false, false, null);

        DeliverCallback deliverCallback = (consumerTag, message) -> {
            try {
                String str = new String(message.getBody(), "UTF-8");
                System.out.println("1@" + str);
                Thread.sleep(Integer.parseInt(str) * 1000);
                System.out.println("1#" + str);
            } catch (Exception e) {
                e.printStackTrace();
            }
            channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
        };

        channel.basicConsume(QUEUE_NAME, false, deliverCallback, cancelCallback -> {
        });
    }
}
