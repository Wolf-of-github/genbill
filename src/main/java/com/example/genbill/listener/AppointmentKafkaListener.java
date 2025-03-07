package com.example.genbill.listener;

import com.example.genbill.model.ReceivedMessage;
import com.example.genbill.repository.ReceivedMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AppointmentKafkaListener {

    @Autowired
    private ReceivedMessageRepository receivedMessageRepository;

    @KafkaListener(topics = "appointments", groupId = "group_id")
    public void listen(String message) {
        // Create a new ReceivedMessage document with 'received' set to "yes" and the current time
        ReceivedMessage receivedMessage = new ReceivedMessage("yes", new Date());

        // Save the document to MongoDB
        receivedMessageRepository.save(receivedMessage);

        // Log the received message (optional)
        System.out.println("Received Kafka message: " + message);
    }
}