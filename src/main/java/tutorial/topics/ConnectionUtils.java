package tutorial.topics;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

class ConnectionUtils {

    static final String TOPIC_EXCHANGE_NAME = "top_ex";

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
