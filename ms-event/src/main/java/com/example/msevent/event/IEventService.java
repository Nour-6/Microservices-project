package com.example.msevent.event;

import java.util.List;

public interface IEventService {
    List<EventDTO> findAllEvents();
    EventDTO getEventById(String eventId);
    EventDTO saveEvent(EventDTO eventDTO);
    void deleteEventById(String eventId);
}

