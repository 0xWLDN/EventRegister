package com.example.EventRegister.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "events")
public class Event {

    @Id
    private String id;
    private String creatorId;
    private String title;
    private String description;
    private List<Question> questions;

    // Inner class for Question
    public static class Question {
        private String id;
        private String type; // "MCQ" or "TEXT"
        private String question;
        private List<String> options; // Only for MCQ

        // Getters and setters
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }

        public String getType() { return type; }
        public void setType(String type) { this.type = type; }

        public String getQuestion() { return question; }
        public void setQuestion(String question) { this.question = question; }

        public List<String> getOptions() { return options; }
        public void setOptions(List<String> options) { this.options = options; }
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getCreatorId() { return creatorId; }
    public void setCreatorId(String creatorId) { this.creatorId = creatorId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<Question> getQuestions() { return questions; }
    public void setQuestions(List<Question> questions) { this.questions = questions; }
}

