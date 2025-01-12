package org.example.queue;

import jakarta.jms.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

public class SyncConsumer {

    public static void main(String[] args) throws NamingException, JMSException {
        // Lookup JMS resources from the Payara server
        Context context = getInitialContext();
        Queue queue = (Queue) context.lookup("jms/QueueSync");
        JMSContext jmsContext = ((ConnectionFactory) context.lookup("jms/TestFactory")).createContext();
        JMSConsumer jmsConsumer = jmsContext.createConsumer(queue);

        System.out.println("Waiting for messages...");

        while (true) {
            // Receive messages synchronously
            Message message = jmsConsumer.receive(); // Blocks until a message is received
            if (message != null) {
                String messageBody = message.getBody(String.class);
                System.out.println("Message received: " + messageBody);
            }
        }
    }

    public static Context getInitialContext() throws NamingException {
        Properties properties = new Properties();
        properties.setProperty("java.naming.factory.initial", "com.sun.enterprise.naming.SerialInitContextFactory");
        properties.setProperty("java.naming.factory.url.pkgs", "com.sun.enterprise.naming");
        properties.setProperty("java.naming.provider.url", "iiop://localhost:3700");
        return new InitialContext(properties);
    }
}
