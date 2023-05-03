package com.shpp.rstefanyshyn;


import javax.jms.*;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Producer implements Constant {
    private static final Logger logger = LoggerFactory.getLogger(Producer.class);
    private final MessageProducer messageProducer;
    private final TextMessage message;
    private final Connection connection;
    private final Session session;


    public Producer() throws Exception {
        try {
            ActiveMQConnectionFactory factory = Services.activeMQConnectionFactory();
            connection = factory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Destination queue = session.createQueue(QUEUE);

            messageProducer = session.createProducer(queue);
            messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            message = session.createTextMessage();
            logger.debug("Producer was started!");
        } catch (JMSException e) {
            logger.error("Error of Producer", e);
            throw new Exception(e);
        }
        logger.info("Producer was started");
    }


    public void send(String msg) {
        try {
            message.setText(String.valueOf(msg));
            messageProducer.send(message);
        } catch (JMSException e) {
            logger.error("Message don't send", e);
        }

    }

    public void stop() {
        try {
            session.close();
            connection.close();
            logger.warn("Producer is   stopped!");
        } catch (JMSException e) {
            logger.error("Producer  stopped!", e);
            throw new RuntimeException(e);
        }
    }


}