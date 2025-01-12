package org.example.topic;

import jakarta.jms.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;
import java.util.Properties;

public class TopicSubscriber {

    public static void main(String[] args) throws NamingException, JMSException {
        // Look up the resources configured on Payara
        Context context = getInitialContext();
        Topic topic = (Topic) context.lookup("jms/TestTopic");
        JMSContext jmsContext = ((ConnectionFactory) context.lookup("jms/TestTopicFactory")).createContext();
        JMSConsumer jmsConsumer = jmsContext.createConsumer(topic);

        // Set up the message listener to handle messages asynchronously
        System.out.println("Waiting for messages on the Topic...");

        // Asynchronous message receiving
        jmsConsumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                try {
                    String receivedMessage = message.getBody(String.class);
                    System.out.println("Received message: " + receivedMessage);
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });

        // program running so it can continue receiving messages
        System.out.println("Press Enter to exit...");
        try {
            System.in.read(); // subscriber running until Enter is pressed
        } catch (IOException e) {
            e.printStackTrace();
        }

        jmsContext.close(); // Close the context when done
    }

    public static Context getInitialContext() throws NamingException {
        Properties properties = new Properties();
        properties.setProperty("java.naming.factory.initial", "com.sun.enterprise.naming.SerialInitContextFactory");
        properties.setProperty("java.naming.factory.url.pkgs", "com.sun.enterprise.naming");
        properties.setProperty("java.naming.provider.url", "iiop://localhost:3700");
        return new InitialContext(properties);
    }
}