package tutorial.routing;


import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ConnectionUtils {

    public static Connection getConnection() {
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
        } finally {
            return connection;
        }
    }

    public static final String EXCHANGE_NAME = "routing_log_exchange";

}
