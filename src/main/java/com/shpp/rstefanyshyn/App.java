package com.shpp.rstefanyshyn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class App implements Constant {


    private static final Logger logger = LoggerFactory.getLogger(App.class);


    public static void main(String[] args) throws Exception  {
        int messageAmount;
        if (args.length != 0) {
            try {
                messageAmount = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
              //  e.printStackTrace();
                logger.error("dfdsfdsf",e);
                messageAmount = Integer.parseInt(COUNT_MESSAGES);
            }
        } else {
            messageAmount = Integer.parseInt(COUNT_MESSAGES);

        }
        GeneratePojo pojo = new GeneratePojo();
        Producer producer = new Producer();
        pojo.generateMessages(producer, messageAmount);
        ValidAndWrite read = new ValidAndWrite();
        read.readText();
    }
}
