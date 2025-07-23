package com.example.EventRegister.service;

import com.example.EventRegister.model.Event;
import com.example.EventRegister.model.User;
import com.example.EventRegister.repository.EventRepository;
import com.example.EventRegister.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepository eventRepo;
    private final UserRepository userRepo;

    public EventService(EventRepository eventRepo, UserRepository userRepo) {
        this.eventRepo = eventRepo;
        this.userRepo = userRepo;
    }

    public List<Event> getEventsByCreatorEmail(String email) {
        User user = userRepo.findByEmail(email).orElseThrow();
        return eventRepo.findByCreatorId(user.getId());
    }

    public void createEvent(Event event, String creatorEmail) {
        User user = userRepo.findByEmail(creatorEmail).orElseThrow();
        event.setCreatorId(user.getId());
        eventRepo.save(event);
    }

    public List<Event> getAllEvents() {
        return eventRepo.findAll();
    }

    public Optional<Event> getEventById(String id) {
        return eventRepo.findById(id);
    }

    public Map<String, String> mapEventIdsToTitles() {
        return eventRepo.findAll().stream()
                .collect(Collectors.toMap(Event::getId, Event::getTitle));
    }

    public Map<String, Map<String, String>> mapEventIdToQuestionText() {
        List<Event> events = eventRepo.findAll();
        Map<String, Map<String, String>> result = new HashMap<>();

        for (Event event : events) {
            Map<String, String> qMap = new HashMap<>();
            for (Event.Question q : event.getQuestions()) {
                qMap.put(q.getId(), q.getQuestion());
            }
            result.put(event.getId(), qMap);
        }
        return result;
    }

    public void deleteEventById(String eventId) {
        eventRepo.deleteById(eventId);
    }

}

