package com.shpp.rstefanyshyn;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.OngoingStubbing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.jms.*;

import java.util.concurrent.BlockingQueue;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

class ConsumerTest implements Constant {


    private static final Logger logger = LoggerFactory.getLogger(ConsumerTest.class);
    @Mock
    private Connection connection;

    @Mock
    private Producer producer;
    @Mock
    private Session session;

    @Mock
    private Destination queue;


    @Mock
    private MessageConsumer messageConsumer;

    @Mock
    private ActiveMQConnectionFactory factory;

    @Mock
    Message message;
    @Mock
    TextMessage textMessage;
    @Mock
    Consumer consumer;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);


    }


    @Test
    void testRun() throws Exception {
        Destination queue = null;
        when(messageConsumer.receive()).thenReturn(message);

        assertEquals(session.createQueue(QUEUE), (Queue) queue);

        when(textMessage.getText()).
                thenReturn("{\"name\":\"roman_test\",\"count\":10,\"createdAt\":\"2023-06-06T13:12:56.0546538\"}");
        logger.info(textMessage.getText());
        assertEquals(textMessage.getText(),
                "{\"name\":\"roman_test\",\"count\":10,\"createdAt\":\"2023-06-06T13:12:56.0546538\"}");
        assertNotEquals(textMessage.getText(),
                "{\"name\":\"name\",\"count\":27,\"createdAt\":\"2023-04-30T14:40:23.0389838\"}");
        when(consumer.receiveMessage()).thenReturn("test");
        assertEquals("test", consumer.receiveMessage());
        when(consumer.receiveMessage()).thenReturn("poison_pill");
        assertTrue("true", consumer.receiveMessage().equals(POISON_PILL));
        when(consumer.receiveMessage()).thenReturn("test");


    }

    @Test
    void closeConnection() throws Exception {

        assertFalse(consumer.closeConnection());
        when(consumer.closeConnection()).thenReturn(true);
        assertTrue(consumer.closeConnection());
        logger.info(consumer.closeConnection() + "");

        // verify(consumer).closeConnection();

    }


}

