package com.shpp.rstefanyshyn;


import javax.jms.*;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Consumer implements Constant {


    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);
    private final Connection connection;
    private final Session session;
    private final MessageConsumer messageConsumer;
    private final Destination queue;
    private final ActiveMQConnectionFactory factory;
    public Consumer() throws Exception {
        try {
             factory = Services.activeMQConnectionFactory();
            connection = factory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
             queue = session.createQueue(QUEUE);
            messageConsumer = session.createConsumer(queue);


            logger.debug("Consumer was started!");
        } catch (JMSException e) {
            logger.error("Error of Consumer", e);
            throw new Exception(e);
        }
    }


    public String receiveMessage() throws Exception {
        Message message = messageConsumer.receive();
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            if (((TextMessage) message).getText().equals(POISON_PILL)) {
                logger.warn("Received poison");
                return null;

            }
            return textMessage.getText();
        }

        return null;
    }

    public boolean closeConnection() throws Exception {
        try {
            messageConsumer.close();
            session.close();
            connection.close();
            logger.info("Closed consumer connection");

        } catch (Exception e) {
            logger.info("Closed consumer not connection");
            return true;

        }
        return false;
    }


}