package com.shpp.rstefanyshyn;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PojoMessageTest {
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        //   Mockito.when(producer.send(pojo));

    }
    LocalDateTime periodEnd = LocalDateTime.now();
    PojoMessage pojoMessage=new PojoMessage("user",22,periodEnd);
    PojoMessage pojoMessage1=new PojoMessage("user",22,periodEnd);
    @Test
    void getCount() {
        assertEquals(pojoMessage.getCount(),22) ;
    }



    @Test
    void getName() {
        assertEquals(pojoMessage.getName(),"user") ;

    }



    @Test
    void getDateTime() {
        assertEquals(pojoMessage.getDateTime(),periodEnd) ;
    }



        @Test
    void testToString() {

        assertEquals(pojoMessage.toString(), new StringBuilder().append("pojo{").append("name: ").append('"').append("user").append('"').append(", count: ").append(22).append(", created_at: ").append(periodEnd).append('}').toString());
    }
}