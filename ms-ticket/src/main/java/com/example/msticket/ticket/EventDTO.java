package com.example.msticket.ticket;

import lombok.Builder;

@Builder
public record EventDTO(String eventId, String eventPlace, String eventDate) {
}
