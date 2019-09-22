package com.example.almasud.fundamental.content_provider;

public class TourEvent {
    private int eventId;
    private String eventName;
    private String destination;

    public TourEvent(String eventName, String destination) {
        this.eventName = eventName;
        this.destination = destination;
    }

    public TourEvent(int eventId, String eventName, String destination) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.destination = destination;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
