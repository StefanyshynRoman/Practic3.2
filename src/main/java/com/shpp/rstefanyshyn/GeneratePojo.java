package com.shpp.rstefanyshyn;


import org.apache.activemq.util.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.stream.Stream;

public class GeneratePojo implements Constant {
    Logger logger = LoggerFactory.getLogger(GeneratePojo.class);
   private Timer timer;



    public GeneratePojo() {
        // TODO document why this constructor is empty
    }

    public String randomName() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int randomLimit=5;
        Random random = new Random();
        int targetStringLength = random.nextInt(randomLimit) + randomLimit;

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }


    public LocalDateTime randomDate() {
        int yearAg0=10;
        LocalDateTime periodStart = LocalDateTime.now().minusYears(yearAg0);
        LocalDateTime periodEnd = LocalDateTime.now();
        int randomSeconds = new Random().nextInt((int) periodStart.until(periodEnd, ChronoUnit.SECONDS));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime anyTime = periodStart.plusSeconds(randomSeconds);
        String formatDateTime = anyTime.format(formatter);
        return LocalDateTime.parse(formatDateTime, formatter);
    }

    public Integer randomCount() {

        Random random = new Random();
        return random.nextInt(RANDOM_COUNT);
    }



    public void generateMessages(Producer producer, Integer maxN) {
        logger.info("generate POJO ");
        StopWatch stopWatch=new StopWatch();
        stopWatch.restart();
        Stream.generate(() -> new PojoMessage(randomName(), randomCount(),
                        randomDate()))
                .limit(maxN).takeWhile(p -> stopWatch.taken()<Long.parseLong(STOP_TIME)*THOUSAND)
                .forEach(msg -> {
                    producer.send(Services.toJson(msg));
                    COUNTER_SEND_MESS.getAndIncrement();
                    // logger.info("№ "+COUNTER_SEND_MESS+ "  " + msg+ "  ");
                });
        logger.info("RPS_SEND_MESSAGES  {}   ", (COUNTER_SEND_MESS.doubleValue() / stopWatch.taken()) * THOUSAND);
        producer.send(POISON_PILL);
        producer.closeConectionProducer();
        logger.info("Send "+COUNTER_SEND_MESS);

    }

}