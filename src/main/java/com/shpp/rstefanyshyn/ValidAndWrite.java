package com.shpp.rstefanyshyn;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import com.opencsv.CSVWriter;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.util.Arrays;
import java.util.Set;

public class ValidAndWrite  implements Constant{



    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ValidAndWrite.class);


    public  void readText() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<PojoMessage>> errors;
        try (FileWriter fileWriter = new FileWriter(VALID);
             FileWriter writerError = new FileWriter(NOT_VALID)) {
            PojoMessage pojo;
            Consumer consumer = new Consumer();

            while (true) {
                try {
                    pojo = Services.toPojo(consumer.receiveMessage());
                    errors = validator.validate(pojo);
                }catch (IllegalArgumentException e){
                    consumer.closeConnection();
                    break;
                }
                if (errors.isEmpty()) fileWriter.append(stringCSVPojo(pojo));
                else writerError.append(stringCSVError(pojo, errors));
                fileWriter.flush();
                writerError.flush();

            }

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