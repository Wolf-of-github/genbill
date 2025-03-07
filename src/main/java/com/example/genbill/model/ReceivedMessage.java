package com.example.genbill.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "received_messages")
public class ReceivedMessage {

    @Id
    private String id;
    private String patientId; // Added patientId field
    private Date time; // Time extracted from the message
    private double billAmount; // The computed bill

    public ReceivedMessage(String patientId, Date time, double billAmount) {
        this.patientId = patientId;
        this.time = time;
        this.billAmount = billAmount;
    }

    // Getters and setters
    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public double getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(double billAmount) {
        this.billAmount = billAmount;
    }
}