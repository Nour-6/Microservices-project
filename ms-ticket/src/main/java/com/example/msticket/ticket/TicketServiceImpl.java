package com.example.msticket.ticket;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class TicketServiceImpl implements ITicketService {
    private TicketRepository ticketRepository;
    private EventClient eventClient;
    private final TicketMapper ticketMapper;

    public TicketDTO getTicketById(Long id) {
        return ticketRepository.findById(id).map(ticket -> {
            EventDTO eventDTO = eventClient.getEventById(ticket.getEventId());
            TicketDTO ticketDTO = ticketMapper.toDto(ticket);
            return new TicketDTO(ticketDTO.ticketId(), ticketDTO.price(), ticketDTO.eventId(), eventDTO);
        }).orElseThrow(() -> new IllegalArgumentException("Ticket not found"));
        }

    public TicketDTO saveTicket(TicketDTO ticketDTO) {
        Ticket ticket = ticketMapper.toEntity(ticketDTO);
        ticket = ticketRepository.save(ticket);
        return ticketMapper.toDto(ticket);
    }

    public List<TicketDTO> findAllTickets() {
        return ticketRepository.findAll().stream()
                .map(ticket -> {
                    EventDTO eventDTO = eventClient.getEventById(ticket.getEventId());
                    TicketDTO ticketDTO = ticketMapper.toDto(ticket);
                    return new TicketDTO(ticketDTO.ticketId(), ticketDTO.price(), ticketDTO.eventId(), eventDTO);
                })
                .collect(Collectors.toList());
    }

    public void deleteTicketById(Long id) {
        ticketRepository.deleteById(id);
    }

}

