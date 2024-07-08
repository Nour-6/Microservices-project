package com.example.msevent.event;

import lombok.Builder;

@Builder
public record EventDTO(String eventId, String eventPlace, String eventDate) {

    // Static method to map from Stock entity to StockDTO
    public static EventDTO mapFromEntity(Event event) {
        return EventMapper.INSTANCE.toDto(event);
    }

    // Static method to map from StockDTO to Stock entity
    public static Event mapToEntity(EventDTO eventDTO) {
        return EventMapper.INSTANCE.toEntity(eventDTO);
    }

}
