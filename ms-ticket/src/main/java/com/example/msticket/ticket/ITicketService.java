package com.example.msticket.ticket;


import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface ITicketService {
    TicketDTO getTicketById(Long ticketId);
    TicketDTO saveTicket(TicketDTO ticketDTO);
    void sendTicket(EventDTO eventDTO) throws JsonProcessingException;
    List<TicketDTO> findAllTickets();
    void deleteTicketById(Long ticketId);
}

