package com.shpp.rstefanyshyn;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Services implements  Constant{
    private static final Logger log = LoggerFactory.getLogger(Services.class);
    private static final ObjectMapper mapper = new ObjectMapper();
    static {
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.registerModule(new JavaTimeModule());
    }

    private Services() {
    }

    public static String toJson(PojoMessage pojo){
        String msg = null;
        try {
            msg = mapper.writeValueAsString(pojo);
        } catch (JsonProcessingException e) {
            log.warn("Json error of toString",e);
        }
        return msg;
    }

    public static PojoMessage toPojo(String json) {
        PojoMessage pojo = null;
        try {
            pojo = mapper.readValue(json, PojoMessage.class);
        } catch (JsonProcessingException e) {
            log.warn("Json error of toPojoMessage",e);
        }
        return pojo;
    }
    public static ActiveMQConnectionFactory activeMQConnectionFactory() {

        ActiveMQConnectionFactory connectionFactory =
                new ActiveMQConnectionFactory(BROKER_URL);

        connectionFactory.setUserName(USER);
        connectionFactory.setPassword(PASSWORD);
        return connectionFactory;
    }

}