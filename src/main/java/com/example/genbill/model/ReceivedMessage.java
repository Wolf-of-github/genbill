package com.example.genbill.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "received_messages")
public class ReceivedMessage {

    @Id
    private String id;
    private String received;
    private Date time;

    public ReceivedMessage(String received, Date time) {
        this.received = received;
        this.time = time;
    }

    // Getters and setters
    public String getReceived() {
        return received;
    }

    public void setReceived(String received) {
        this.received = received;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}