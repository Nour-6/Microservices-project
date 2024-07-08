package com.example.msticket.ticket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
@Slf4j
@RequiredArgsConstructor
public class TicketController {
    private final ITicketService ticketService;

    @GetMapping
    public List<TicketDTO> getAllTickets() {
        return ticketService.findAllTickets();
    }

    @GetMapping("/{id}")
    public TicketDTO getTicketById(@PathVariable("id") Long id) {return ticketService.getTicketById(id);}

    @PostMapping
    public TicketDTO createTicket(@RequestBody TicketDTO ticketDTO) {
        return ticketService.saveTicket(ticketDTO);
    }

    @PutMapping("/{id}")
    public TicketDTO updateTicket(@PathVariable Long id, @RequestBody TicketDTO ticketDTO) {return ticketService.saveTicket(ticketDTO);}

    @DeleteMapping("/{id}")
    public void deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicketById(id);
        }

}
