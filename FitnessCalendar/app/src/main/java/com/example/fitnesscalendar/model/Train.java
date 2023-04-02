package com.example.fitnesscalendar.model;

import java.io.Serializable;

/*
This is the model class of train. This class take care of information related to train, which would
contain train type and calories. One should use this class everywhere else than calling FireBaseFetcher.
The EventViewModel class should render this class.
 */
public class Train implements Serializable {

    private String trainingName;
    private boolean trainingIsAn;
    private boolean trainingIsCa;
    private int trainingCalories;

    //public constructor
    public Train(String trainingName, boolean trainingIsAn, boolean trainingIsCa, int trainingCalories) {
        this.trainingName = trainingName;
        this.trainingIsAn = trainingIsAn;
        this.trainingIsCa = trainingIsCa;
        this.trainingCalories = trainingCalories;
    }

    /*
    belows are all getters and setters
     */
    public String getTrainingName() {
        return trainingName;
    }

    public void setTrainingName(String trainingName) {
        this.trainingName = trainingName;
    }

    public boolean getTrainingIsAn() {
        return trainingIsAn;
    }

    public void setTrainingIsAn(boolean trainingIsAn) {
        this.trainingIsAn = trainingIsAn;
    }

    public boolean getTrainingIsCa() {
        return trainingIsCa;
    }

    public void setTrainingIsCa(boolean trainingIsCa) {
        this.trainingIsCa = trainingIsCa;
    }

    public int getTrainingCalories() {
        return trainingCalories;
    }

    public void setTrainingCalories(int trainingCalories) {
        this.trainingCalories = trainingCalories;
    }

}
