package com.example.msevent.event;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class EventServiceImpl implements IEventService {
    private EventRepository repository;

    public List<EventDTO> findAllEvents() {
        return repository.findAll().stream()
                .map(EventDTO::mapFromEntity)
                .collect(Collectors.toList());
    }

    public EventDTO getEventById(String eventId) {
        Event event = repository.findById(eventId).orElse(null);
        return EventDTO.mapFromEntity(event);
    }

    public EventDTO saveEvent(EventDTO eventDTO) {
        Event event = EventDTO.mapToEntity(eventDTO);
        event = repository.save(event);
        return EventDTO.mapFromEntity(event);
    }

    public void deleteEventById(String eventId) {
        repository.deleteById(eventId);
    }
}

