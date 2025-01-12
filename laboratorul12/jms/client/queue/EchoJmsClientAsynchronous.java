package org.example.queue;

import jakarta.jms.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class EchoJmsClientAsynchronous  implements MessageListener {

    public static void main(String[] args) throws JMSException, NamingException, IOException {

        EchoJmsClientAsynchronous echoJmsClientAsynchronous = new EchoJmsClientAsynchronous ();
        Context context = echoJmsClientAsynchronous.getInitialContext();
        Queue queue1 = (Queue) context.lookup("jms/TestQueue");
        Queue queue2 = (Queue) context.lookup("jms/Queue2");
        JMSContext jmsContext = ((ConnectionFactory) context.lookup("jms/TestFactory")).createContext();
        jmsContext.createConsumer(queue2).setMessageListener(echoJmsClientAsynchronous );
        JMSProducer jmsProducer = jmsContext.createProducer();
        BufferedReader bufferedReader = new java.io.BufferedReader(new InputStreamReader(System.in));

        String messageToSend = null;

        System.out.println("You are now connected...");

        while (true) {

            messageToSend = bufferedReader.readLine();
            if (messageToSend.equalsIgnoreCase("exit")) {

                jmsContext.close();
                System.out.println("Good Bye");
                System.exit(0);
            } else {
                jmsProducer.send(queue1, messageToSend);
            }

        }

    }

    @Override
    public void onMessage(Message message) {

        try {
            System.out.println(message.getBody(String.class));

        } catch (JMSException e) {
            e.printStackTrace();
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