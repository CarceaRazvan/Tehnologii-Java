package org.example.laboratorul12.messages.queue;

import jakarta.annotation.Resource;
import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.EJB;
import jakarta.ejb.MessageDriven;
import jakarta.inject.Inject;
import jakarta.jms.*;
import org.example.laboratorul12.messages.Receiver;

@MessageDriven(name = "TestQueue", mappedName = "jms/TestQueue", activationConfig = {
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "jakarta.jms.Queue")

})
public class MsgListener implements MessageListener{

    @EJB
    private Receiver receiver;

    @Inject
    JMSContext jmsContext;

    @Resource(mappedName = "jms/Queue2")
    Queue queue2;

//    @Override
//    public void onMessage(Message msg) {
//
//        try {
//            receiver.receiver(msg.getBody(String.class));
//        } catch (JMSException e) {
//            throw new RuntimeException(e);
//        }
//    }

    @Override
    public void onMessage(Message msg) {

        try {

            String stringMessage = msg.getBody(String.class);
            System.out.println("EchoServer received the following message: "+stringMessage);
            jmsContext.createProducer().send(queue2, "echo " + stringMessage);

        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }

}
