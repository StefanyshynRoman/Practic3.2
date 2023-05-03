package com.shpp.rstefanyshyn;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class Timer implements Constant{
    private final int second;

    private long startTime = 0;
    private long stopTime = 0;
    private boolean running = false;
    private static final Logger logger = LoggerFactory.getLogger(Timer.class);

    public Timer(int second) {
        this.second = second;

    }

    public void startAndStop() throws InterruptedException {

        startTimer();
        Thread.sleep((long) (second * THOUSAND));
        stopTimer();


    }

    public void startTimer() {
        this.startTime = System.currentTimeMillis();
        this.running = true;
        logger.info("Start timer " + LocalDateTime.now());
    }


    public void stopTimer() {
        this.stopTime = System.currentTimeMillis();
        this.running = false;
        logger.info("Stop timer  " + LocalDateTime.now());
    }

    public boolean isRunning() {
        return running;
    }


    //elapsed time in seconds
    public long getElapsedTimeSecs() {
        long elapsed;
        if (running) {
            elapsed = (long) ((System.currentTimeMillis() - startTime) / THOUSAND);
        } else {
            elapsed = (long) ((stopTime - startTime) / THOUSAND);
        }
        return elapsed;
    }

}
