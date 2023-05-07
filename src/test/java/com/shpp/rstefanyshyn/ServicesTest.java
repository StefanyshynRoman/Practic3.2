package com.shpp.rstefanyshyn;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class ServicesTest implements Constant{
    private static final Logger logger = LoggerFactory.getLogger(ServicesTest.class);
    @Mock
    private static final ObjectMapper mapper = new ObjectMapper();
    static {
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.registerModule(new JavaTimeModule());
    }
    @Mock
    ActiveMQConnectionFactory connectionFactory =
            new ActiveMQConnectionFactory(BROKER_URL);
    @Mock
    PojoMessage pojo;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        //   Mockito.when(producer.send(pojo));

    }
    @Test
    void toJson() throws JsonProcessingException {
        String msg = "{\"name\":\"roman_test\",\"count\":10,\"createdAt\":\"2023-06-06T13:12:56.0546538\"}";
        when(mapper.writeValueAsString(pojo)).thenReturn(msg);
       assertEquals(msg,mapper.writeValueAsString(pojo));
    }

    @Test
    void toPojo() throws JsonProcessingException {
        String json = null;
        PojoMessage pojo = null;
        when(mapper.readValue(json,PojoMessage.class)).thenReturn(pojo);
        assertEquals(pojo, mapper.readValue(json,PojoMessage.class));

    }

    @Test
    void activeMQConnectionFactory() {
        ActiveMQConnectionFactory connectionFactory =
                mock(ActiveMQConnectionFactory.class);
        connectionFactory.setUserName(USER);
        verify(connectionFactory,times(1)).setUserName("admin");
        connectionFactory.setPassword(PASSWORD);
        verify(connectionFactory,times(1)).setPassword("romanpz051romanpz051");
    }
}