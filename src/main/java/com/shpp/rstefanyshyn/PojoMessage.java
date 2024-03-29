package com.shpp.rstefanyshyn;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;


public class PojoMessage implements Serializable {
    @NotNull
    @Size(min = 7,message ="gk;gnfg,.fgmfd.gdfmg")
    @Pattern(regexp = ".*a.*")

    private String name;
    @Min(value = 10,message ="4444444444444444444444444444444")
    private int count;
    @JsonDeserialize
    private LocalDateTime dateTime;

    public PojoMessage(String name, int count, LocalDateTime dateTime) {
        this.name = name;
        this.count = count;
        this.dateTime = dateTime;

    }

    public PojoMessage() {

    }


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }


    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }


    @Override
    public String toString() {
        return "pojo{" +
                "name: " + '"' + name + '"' +
                ", count: " + count +
                ", created_at: " + dateTime +
                '}';

    }
}