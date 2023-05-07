package com.shpp.rstefanyshyn;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static com.shpp.rstefanyshyn.Constant.POISON_PILL;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class GeneratePojoTest {
    @Mock
    Producer producer;
    @Mock
    PojoMessage pojo;
    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
        //   Mockito.when(producer.send(pojo));

    }
    private static final Logger logger =
            LoggerFactory.getLogger(GeneratePojoTest.class);
    LocalDateTime periodEnd = LocalDateTime.now();
    PojoMessage pojoMessage = new PojoMessage("user",22,periodEnd);
    GeneratePojo generatePojo = new GeneratePojo();

    @Test
    void randomName() {
        assertNotEquals(pojoMessage.getName(), generatePojo.randomName());

    }

    @Test
    void randomDate() {
        assertNotEquals(generatePojo.randomCount(), pojoMessage.getDateTime());

    }

    @Test
    void randomCount() {
        assertNotEquals(pojoMessage.getCount(), 21);
        assertNotEquals(generatePojo.randomCount(),pojoMessage.getCount());

    }

    @Test
    void generateMessages() {
        //String msg = null;
        PojoMessage msg = null;
        Producer producer1=mock(Producer.class);
        producer1.send("hello");
        verify(producer1,times(1)).send("hello");




    }
}