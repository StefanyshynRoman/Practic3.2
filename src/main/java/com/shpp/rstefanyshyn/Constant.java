package com.shpp.rstefanyshyn;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

public interface Constant {
    GetProperty PROPERTY = new GetProperty("config.properties");

    String POISON_PILL = PROPERTY.getValueFromProperty("poison");

    String STOP_TIME = PROPERTY.getValueFromProperty("timer");

    String QUEUE=PROPERTY.getValueFromProperty("queue");
    String BROKER_URL=PROPERTY.getValueFromProperty("broker");
    String USER=PROPERTY.getValueFromProperty("user");
    String PASSWORD=PROPERTY.getValueFromProperty("password");
    String COUNT_MESSAGES = PROPERTY.getValueFromProperty("count");
    AtomicInteger COUNTER_SEND_MESS = new AtomicInteger(0);
    AtomicInteger COUNTER_RECEIVE_MESS = new AtomicInteger(0);
    Integer RANDOM_COUNT=100;
    double THOUSAND =1000;
    String VALID = "valid.csv";
    String NOT_VALID = "not-invalid.csv";
}
