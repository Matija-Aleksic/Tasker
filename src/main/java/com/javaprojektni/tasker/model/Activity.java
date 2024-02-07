package com.javaprojektni.tasker.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

public class Activity implements Serializable {

    private Date madeOn;
    private String desc;
    private String madeBys;

    public Activity(Date madeOn, String desc, String madeBys) {
        this.madeOn = madeOn;
        this.desc = desc;
        this.madeBys = madeBys;
    }

    public Date getMadeOn() {
        return madeOn;
    }

    public void setMadeOn(Date madeOn) {
        this.madeOn = madeOn;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getMadeBys() {
        return madeBys;
    }

    public void setMadeBys(String madeBys) {
        this.madeBys = madeBys;
    }
}
