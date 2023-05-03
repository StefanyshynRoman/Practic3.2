package com.shpp.rstefanyshyn;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import org.apache.activemq.util.StopWatch;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.util.Set;

public class ValidAndWrite implements Constant {
    private Timer timer;

    StopWatch stopWatch = new StopWatch();
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ValidAndWrite.class);

    private void startAndStopTimer() throws InterruptedException {
        timer = new Timer(Integer.parseInt(STOP_TIME));
        timer.startAndStop();
        logger.info("Timer  was stopped!");
        logger.info("Timer is second {}", timer.getElapsedTimeSecs());
    }

    private void newTread() {
        Thread thread = new Thread(() -> {
            try {
                startAndStopTimer();
            } catch (InterruptedException e) {
                logger.error("Interrupted!", e);
                // Restore interrupted state...
                Thread.currentThread().interrupt();
            }
        });
        thread.start();

    }
    private void newTread1() {
        Thread thread = new Thread(() -> {

                stopWatch.restart();


        });
        thread.start();

    }


    public void readText() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<PojoMessage>> errors;

        try (FileWriter fileWriter = new FileWriter(VALID);
             FileWriter writerError = new FileWriter(NOT_VALID)) {
            PojoMessage pojo;
            Consumer consumer = new Consumer();

            stopWatch.restart();
            while (true) {
                try {
                    String consumerM=consumer.receiveMessage();
                    if (consumerM==null){
                      logger.info("Count receive message  "+ COUNTER_RECEIVE_MESS);
                        logger.info("RPS_RECEIVE_MESSAGE  {}   ", (COUNTER_RECEIVE_MESS.doubleValue() / stopWatch.taken()) * THOUSAND);
                        consumer.closeConnection();
                        break;
                    }
                    pojo = Services.toPojo(consumerM);
                    COUNTER_RECEIVE_MESS.incrementAndGet();
                    errors = validator.validate(pojo);
                } catch (IllegalArgumentException e) {
                    consumer.closeConnection();
                    break;
                }
                if (errors.isEmpty()) fileWriter.append(stringCSVPojo(pojo));
                else writerError.append(stringCSVError(pojo, errors));
                fileWriter.flush();
                writerError.flush();

            }

            stopWatch.taken();
           // logger.info("RPS_RECC{}", COUNTER_RECC_MESS.get() / stopWatch.taken() * THOUSAND);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static String stringCSVPojo(PojoMessage pojo) {
        return pojo.getName() + "," + pojo.getCount() + "\n";
    }


    private static String stringCSVError(PojoMessage pojo, Set<ConstraintViolation<PojoMessage>> errors) {
        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        try {

            json = mapper.writeValueAsString("{errors:["
                    + errors.stream().map(e -> e.getPropertyPath().toString() + ":" + e.getMessage())
                    .reduce((a, b) -> a + "," + b).orElse("") + "]}");
        } catch (JsonProcessingException e) {
            logger.warn("Don't write errors", e);
        }
        return pojo.getName() + "," + pojo.getCount() + ", " + json + "\n";
    }
}