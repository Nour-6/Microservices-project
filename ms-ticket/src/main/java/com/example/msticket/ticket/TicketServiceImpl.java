package com.example.msticket.ticket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
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
    private final TicketMapper ticketMapper;
    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;
    private static final String EVENT_SERVICE_URL = "http://localhost:8081/events/";
    private KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC = "ticket-topic";

    public void sendTicket(EventDTO eventDTO) throws JsonProcessingException {
        String eventAsMessage = objectMapper.writeValueAsString(eventDTO);
        kafkaTemplate.send(TOPIC, eventAsMessage);
        log.info("Produced event: {}{}{}", eventDTO.eventId(), eventDTO.eventDate(), eventDTO.eventPlace());
    }

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
                    ResponseEntity<EventDTO> response = restTemplate.exchange(
                            EVENT_SERVICE_URL + ticket.getEventId(),
                            HttpMethod.GET,
                            HttpEntity.EMPTY,
                            EventDTO.class
                    );
                    EventDTO eventDTO = response.getBody();
                    TicketDTO ticketDTO = ticketMapper.toDto(ticket);
                    return new TicketDTO(ticketDTO.ticketId(), ticketDTO.price(), ticketDTO.eventId(), eventDTO);
                })
                .collect(Collectors.toList());
    }

    public void deleteTicketById(Long id) {
        ticketRepository.deleteById(id);
    }

}

