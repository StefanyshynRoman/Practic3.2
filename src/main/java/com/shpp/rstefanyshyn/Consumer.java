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

    public Consumer() throws Exception {
        try {
            ActiveMQConnectionFactory factory = Services.activeMQConnectionFactory();
            connection = factory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination queue = session.createQueue(QUEUE);
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

    public void closeConnection() throws Exception {
        try {
            messageConsumer.close();
            session.close();
            connection.stop();
            logger.info("Closed consumer connection");
            System.exit(1);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }


}