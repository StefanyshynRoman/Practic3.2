package com.shpp.rstefanyshyn;


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

    private String randomName() {
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


    private LocalDateTime randomDate() {
        int yearAg0=10;
        LocalDateTime periodStart = LocalDateTime.now().minusYears(yearAg0);
        LocalDateTime periodEnd = LocalDateTime.now();
        int randomSeconds = new Random().nextInt((int) periodStart.until(periodEnd, ChronoUnit.SECONDS));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime anyTime = periodStart.plusSeconds(randomSeconds);
        String formatDateTime = anyTime.format(formatter);
        return LocalDateTime.parse(formatDateTime, formatter);
    }

    private Integer randomCount() {

        Random random = new Random();
        return random.nextInt(RANDOM_COUNT);
    }

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

    public void generateMessages(Producer producer, Integer maxN) {
        logger.info("generate POJO ");
        newTread();
        Stream.generate(() -> new PojoMessage(randomName(), randomCount(),
                        randomDate()))
                .limit(maxN).takeWhile(p -> timer.isRunning())
                .forEach(msg -> {
                    producer.send(Services.toJson(msg));
                    COUNTER_SEND_MESS.getAndIncrement();
                    //  logger1.info("№ "+COUNTER_SEND_MESS+ "  " + msg+ "  ");
                });
        producer.send(POISON_PILL);
        producer.stop();
        logger.warn(rps());

    }

    public String rps() {
        return "RPS----------------" + Long.parseLong(String.valueOf(COUNTER_SEND_MESS)) / Integer.parseInt(STOP_TIME);
    }
}