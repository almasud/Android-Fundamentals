package com.example.almasud.fundamental.firebase_service;

public class Event {
    private String eventId;
    private String userId;
    private String eventName;
    private double budget;

    public Event() {
    }

    public Event(String eventId, String userId, String eventName, double budget) {
        this.eventId = eventId;
        this.userId = userId;
        this.eventName = eventName;
        this.budget = budget;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }
}
