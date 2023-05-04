package com.shpp.rstefanyshyn;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class GeneratePojoTest implements Constant {
    @Mock
    Producer producer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
         Mockito.when(producer);


    }

    @Test
    void generateMessagesTest() {
        GeneratePojo generatePojo = new GeneratePojo();
        generatePojo.generateMessages(producer, 2);
        Mockito.verify(producer,
                Mockito.times(2)).send(Mockito.anyString());
    }

}