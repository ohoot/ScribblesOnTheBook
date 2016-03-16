package com.example.joo.scribblesonthebook.data.vo;

import java.io.Serializable;

/**
 * Created by Joo on 2016-02-22.
 */
public class EventInfo implements RecommData, Serializable{
    int programId;
    String programName;
    String programImage;
    String programContents;
    String programDate;
    String programLocation;
    String programSponsor;
    String programReservation;
    int price;

    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getProgramImage() {
        return programImage;
    }

    public void setProgramImage(String programImage) {
        this.programImage = programImage;
    }

    public String getProgramContents() {
        return programContents;
    }

    public void setProgramContents(String programContents) {
        this.programContents = programContents;
    }

    public String getProgramDate() {
        return programDate;
    }

    public void setProgramDate(String programDate) {
        this.programDate = programDate;
    }

    public String getProgramLocation() {
        return programLocation;
    }

    public void setProgramLocation(String programLocation) {
        this.programLocation = programLocation;
    }

    public String getProgramSponsor() {
        return programSponsor;
    }

    public void setProgramSponsor(String programSponsor) {
        this.programSponsor = programSponsor;
    }

    public String getProgramReservation() {
        return programReservation;
    }

    public void setProgramReservation(String programReservation) {
        this.programReservation = programReservation;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
