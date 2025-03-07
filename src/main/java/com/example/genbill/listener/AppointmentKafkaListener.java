package com.example.genbill.listener;

import com.example.genbill.model.ReceivedMessage;
import com.example.genbill.repository.ReceivedMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;
import java.util.Random;

@Service
public class AppointmentKafkaListener {

    @Autowired
    private ReceivedMessageRepository receivedMessageRepository;

    private static final double DEFAULT_RATE = 1.0; // Default rate if no specialist matches

    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "appointments", groupId = "group_id")
    public void listen(String message) {
        try {
            // Parse the incoming message to extract fields
            JsonNode jsonNode = objectMapper.readTree(message);
            String patientId = jsonNode.get("patientId").asText();
            String specialist = jsonNode.get("specialist").asText();
            long date = jsonNode.get("date").asLong();
            int duration = jsonNode.get("duration").asInt();

            // Generate the bill using the specialist and duration
            double billAmount = generateBill(specialist, duration);

            // Convert the date (timestamp) into a Date object
            Date appointmentDate = new Date(date);

            // Create the ReceivedMessage object (with patientId, time, and billAmount)
            ReceivedMessage receivedMessage = new ReceivedMessage(patientId, appointmentDate, billAmount);

            // Save the document to MongoDB
            receivedMessageRepository.save(receivedMessage);

            // Log the received message and the generated bill amount
            System.out.println("Received Kafka message: " + message);
            System.out.println("Generated Bill Amount: $" + billAmount);

        } catch (Exception e) {
            // Log any error during message processing
            System.err.println("Error processing Kafka message: " + e.getMessage());
        }
    }

    // Method to generate a bill based on specialist and duration
    private double generateBill(String specialist, int duration) {
        // Get the rate per minute based on the specialist
        double rate = getRateBySpecialist(specialist);

        // Random modifier between 0.9 and 1.4 to simulate some variability
        Random random = new Random();
        double randomModifier = 0.9 + random.nextDouble() * 0.5;

        // Calculate the total bill (duration * rate * random modifier)
        return duration * rate * randomModifier;
    }

    // Get the rate based on the specialist's category
    private double getRateBySpecialist(String specialist) {
        switch (specialist) {
            case "Cardiologist":
                return 2.0; // $2 per minute
            case "Neurologist":
                return 1.8; // $1.8 per minute
            case "Orthopedic Surgeon":
                return 1.5; // $1.5 per minute
            case "Dermatologist":
                return 1.2; // $1.2 per minute
            case "Ophthalmologist":
                return 1.0; // $1 per minute
            case "Pediatrician":
                return 0.8; // $0.8 per minute
            case "Oncologist":
                return 2.5; // $2.5 per minute
            case "Endocrinologist":
                return 1.7; // $1.7 per minute
            case "Gastroenterologist":
                return 1.6; // $1.6 per minute
            case "Pulmonologist":
                return 1.4; // $1.4 per minute
            default:
                return DEFAULT_RATE; // Default rate for unrecognized specialists
        }
    }
}