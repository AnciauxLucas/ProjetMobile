package com.example.mobilegenicotanciaux.model;

import java.io.Serializable;
import java.util.Date;

public class TrainingSchool implements Serializable {

    private Date dateTraining;
    private Formation training;

    public TrainingSchool(Date dateTraining, Formation training) {
        this.dateTraining = dateTraining;
        this.training = training;
    }

    public Date getDateTraining() {
        return dateTraining;
    }

    public Formation getTraining() {
        return training;
    }

    public void setDateTraining(Date dateTraining) {
        this.dateTraining = dateTraining;
    }

    public void setTraining(Formation training) {
        this.training = training;
    }
}
