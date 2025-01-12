package org.example.laboratorul12.messages.topic;

import jakarta.jms.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class TopicPublisher {

    public static void main(String[] args) throws NamingException, JMSException, IOException {
        // Look up the resources configured on Payara
        Context context = getInitialContext();
        Topic topic = (Topic) context.lookup("jms/TestTopic");
        JMSContext jmsContext = ((ConnectionFactory) context.lookup("jms/TestTopicFactory")).createContext();
        JMSProducer jmsProducer = jmsContext.createProducer();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String messageToSend;
        System.out.println("Enter messages to send to the Topic (type 'exit' to quit):");

        // Loop to send messages continuously
        while (true) {
            // Read message from the console
            messageToSend = bufferedReader.readLine();

            // Exit condition
            if ("exit".equalsIgnoreCase(messageToSend)) {
                System.out.println("Exiting...");
                break;
            }

            // Publish the message to the topic
            jmsProducer.send(topic, messageToSend);
            System.out.println("Message sent to the Topic: " + messageToSend);
        }

        jmsContext.close();  // Close the JMS context when done
    }

    public static Context getInitialContext() throws NamingException {
        Properties properties = new Properties();
        properties.setProperty("java.naming.factory.initial", "com.sun.enterprise.naming.SerialInitContextFactory");
        properties.setProperty("java.naming.factory.url.pkgs", "com.sun.enterprise.naming");
        properties.setProperty("java.naming.provider.url", "iiop://localhost:3700");
        return new InitialContext(properties);
    }
}
