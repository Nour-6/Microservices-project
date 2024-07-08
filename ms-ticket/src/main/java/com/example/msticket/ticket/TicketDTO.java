package com.example.msticket.ticket;

import lombok.Builder;

@Builder
public record TicketDTO(Long ticketId, Float price, String eventId, EventDTO eventDTO) {


}
