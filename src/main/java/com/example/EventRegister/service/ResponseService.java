package com.example.EventRegister.service;

import com.example.EventRegister.model.Response;
import com.example.EventRegister.model.Event;
import com.example.EventRegister.repository.ResponseRepository;
import com.example.EventRegister.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ResponseService {

    private final ResponseRepository responseRepo;
    private final EventRepository eventRepo;

    public ResponseService(ResponseRepository responseRepo, EventRepository eventRepo) {
        this.responseRepo = responseRepo;
        this.eventRepo = eventRepo;
    }

    public boolean hasResponded(String userId, String eventId) {
        return responseRepo.existsByUserIdAndEventId(userId, eventId);
    }

    public void saveResponse(Response response, String userId, String eventId) {
        response.setUserId(userId);
        response.setEventId(eventId);
        response.setSubmittedAt(LocalDateTime.now());
        responseRepo.save(response);
    }

    public List<Response> getResponsesByUser(String userId) {
        return responseRepo.findByUserId(userId);
    }
}
