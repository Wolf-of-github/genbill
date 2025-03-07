package com.example.genbill.repository;

import com.example.genbill.model.ReceivedMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReceivedMessageRepository extends MongoRepository<ReceivedMessage, String> {
}