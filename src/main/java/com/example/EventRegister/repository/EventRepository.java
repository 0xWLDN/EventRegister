package com.example.EventRegister.repository;

import com.example.EventRegister.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EventRepository extends MongoRepository<Event, String> {
    List<Event> findByCreatorId(String creatorId);
}

