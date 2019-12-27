package com.example.mobilegenicotanciaux.model;

import java.io.Serializable;

public class Vegetable implements Serializable {

    private Integer id;
    private String name;
    private String description;
    private Double price;
    private Integer hoursBetwWatering;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getHoursBetwWatering() {
        return hoursBetwWatering;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setHoursBetwWatering(Integer hoursBetwWatering) {
        this.hoursBetwWatering = hoursBetwWatering;
    }
}
