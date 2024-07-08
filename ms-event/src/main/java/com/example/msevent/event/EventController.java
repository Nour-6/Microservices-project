package com.example.msevent.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
@Slf4j
@RequiredArgsConstructor
@RefreshScope
public class EventController {
    private final IEventService eventService;

    @GetMapping
    public List<EventDTO> getAllEvents() {
        return eventService.findAllEvents();
    }

    @GetMapping("/{id}")
    public EventDTO getEventById(@PathVariable String id) {
        return eventService.getEventById(id);
    }

    @PostMapping
    public EventDTO createEvent(@RequestBody EventDTO eventDTO) {
        return eventService.saveEvent(eventDTO);
    }

    @PutMapping("/{id}")
    public EventDTO updateEvent(@PathVariable String id, @RequestBody EventDTO eventDTO) {return eventService.saveEvent(eventDTO);}

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable String id) {
            eventService.deleteEventById(id);
    }

}
