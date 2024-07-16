package com.example.msticket.ticket;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class TicketServiceImpl implements ITicketService {
    private TicketRepository ticketRepository;
    private EventClient eventClient;
    private TicketMapper ticketMapper;
    private RestTemplate restTemplate;
    private static final String EVENT_SERVICE_URL = "http://MS-EVENT/events/";

    public TicketDTO getTicketById(Long id) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String token = jwt.getTokenValue();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        return ticketRepository.findById(id).map(ticket -> {
            ResponseEntity<EventDTO> response = restTemplate.exchange(
                    EVENT_SERVICE_URL + ticket.getEventId(),
                    HttpMethod.GET,
                    entity,
                    EventDTO.class
            );
            EventDTO eventDTO = response.getBody();
            TicketDTO ticketDTO = ticketMapper.toDto(ticket);
            return new TicketDTO(ticketDTO.ticketId(), ticketDTO.price(), ticketDTO.eventId(), eventDTO);
        }).orElseThrow(() -> new IllegalArgumentException("Ticket not found"));
        }

    public TicketDTO saveTicket(TicketDTO ticketDTO) {
        EventDTO eventDTO = eventClient.getEventById(ticketDTO.eventId());
        if(eventDTO != null) {
            Ticket ticket = ticketMapper.toEntity(ticketDTO);
            ticketRepository.save(ticket);
            return new TicketDTO(ticketDTO.ticketId(), ticketDTO.price(), ticketDTO.eventId(), eventDTO);
        }
        else throw new IllegalArgumentException("Event not found");
    }

    public List<TicketDTO> findAllTickets() {
        return ticketRepository.findAll().stream()
                .map(ticket -> {
                    TicketDTO ticketDTO = ticketMapper.toDto(ticket);
                    EventDTO eventDTO = eventClient.getEventById(ticketDTO.eventId());
                    return new TicketDTO(ticketDTO.ticketId(), ticketDTO.price(), ticketDTO.eventId(), eventDTO);
                })
                .collect(Collectors.toList());
    }

    public void deleteTicketById(Long id) {
        ticketRepository.deleteById(id);
    }

    @KafkaListener(topics = "event-topic", groupId = "group_id")
    public void consumeEvent(String idEvent) {
       ticketRepository.findAllByEventId(idEvent).forEach(
               ticket -> deleteTicketById(ticket.getId())
       );
        log.info("Consumed event: {}", idEvent);
    }

}

