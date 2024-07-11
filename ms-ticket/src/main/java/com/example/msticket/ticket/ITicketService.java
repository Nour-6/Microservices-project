package com.example.msticket.ticket;



import java.util.List;

public interface ITicketService {
    TicketDTO getTicketById(Long ticketId);
    TicketDTO saveTicket(TicketDTO ticketDTO);
    List<TicketDTO> findAllTickets();
    void deleteTicketById(Long ticketId);
    void consumeEvent(String message);
}

