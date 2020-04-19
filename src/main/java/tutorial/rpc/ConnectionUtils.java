package tutorial.rpc;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ConnectionUtils {

    static final String RPC_QUEUE_NAME = "RPC_QUEUE";

    static Connection getConnection() {
        Connection connection = null;
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            factory.setVirtualHost("/defaultHost");
            factory.setUsername("root");
            factory.setPassword("1");
            connection = factory.newConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

}
