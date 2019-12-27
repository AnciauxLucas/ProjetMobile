package com.example.mobilegenicotanciaux.model;

import java.io.Serializable;
import java.util.Date;

public class Planting implements Serializable {

    private Integer id;
    private Date datePlanted;
    private Date dateHarvested;
    private Boolean isEmpty;
    private Vegetable vegetable;

    public Integer getId() {
        return id;
    }

    public Date getDatePlanted() {
        return datePlanted;
    }

    public Date getDateHarvested() {
        return dateHarvested;
    }

    public Boolean getEmpty() {
        return isEmpty;
    }

    public Vegetable getVegetable() {
        return vegetable;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDatePlanted(Date datePlanted) {
        this.datePlanted = datePlanted;
    }

    public void setDateHarvested(Date dateHarvested) {
        this.dateHarvested = dateHarvested;
    }

    public void setEmpty(Boolean empty) {
        isEmpty = empty;
    }

    public void setVegetable(Vegetable vegetable) {
        this.vegetable = vegetable;
    }
}
