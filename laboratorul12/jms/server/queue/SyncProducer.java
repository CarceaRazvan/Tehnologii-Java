package org.example.laboratorul12.messages.queue;

import jakarta.jms.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class SyncProducer {

    public static void main(String[] args) throws NamingException, JMSException, IOException {
        // Lookup JMS resources from the Payara server
        Context context = getInitialContext();
        Queue queue = (Queue) context.lookup("jms/QueueSync");
        JMSContext jmsContext = ((ConnectionFactory) context.lookup("jms/TestFactory")).createContext();
        JMSProducer jmsProducer = jmsContext.createProducer();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter messages to send to the queue (type 'exit' to quit):");

        String messageToSend;
        while (true) {
            messageToSend = bufferedReader.readLine();
            if ("exit".equalsIgnoreCase(messageToSend)) {
                jmsContext.close();
                System.out.println("Producer exiting...");
                break;
            } else {
                jmsProducer.send(queue, messageToSend);
                System.out.println("Message sent: " + messageToSend);
            }
        }
    }

    public static Context getInitialContext() throws JMSException, NamingException {

        Properties properties = new Properties();
        properties.setProperty("java.naming.factory.initial", "com.sun.enterprise.naming.SerialInitContextFactory");
        properties.setProperty("java.naming.factory.url.pkgs", "com.sun.enterprise.naming");
        properties.setProperty("java.naming.provider.url", "iiop://localhost:3700");
        return new InitialContext(properties);
    }
}