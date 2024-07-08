package com.example.msticket.ticket;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("MS-EVENT")
public interface EventClient {

    @GetMapping("/events/{id}")
    @CircuitBreaker(name="msEvent",fallbackMethod ="fallbackGetEventById")
    EventDTO getEventById(@PathVariable("id") String id);
    default EventDTO fallbackGetEventById(String id, Throwable throwable) {
        return new EventDTO("0", "Fallback event", "0");
    }

}
