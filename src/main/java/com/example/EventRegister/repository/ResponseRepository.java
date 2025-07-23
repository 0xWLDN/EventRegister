package com.example.EventRegister.repository;

import com.example.EventRegister.model.Response;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ResponseRepository extends MongoRepository<Response, String> {
    List<Response> findByEventId(String eventId);
    List<Response> findByUserId(String userId);
    boolean existsByUserIdAndEventId(String userId, String eventId);
}

