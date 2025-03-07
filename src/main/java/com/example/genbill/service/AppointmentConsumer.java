package com.example.genbill.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AppointmentConsumer {

    @KafkaListener(topics = "appointments", groupId = "appointment-consumer")
    public void consume(String message) {
        System.out.print("==============got message========"+message);
        log.info("Received appointment message: {}", message);
    }
}
