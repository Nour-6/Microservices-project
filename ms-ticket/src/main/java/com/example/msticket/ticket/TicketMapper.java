package com.example.msticket.ticket;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "spring")
public interface TicketMapper {
    TicketMapper INSTANCE = Mappers.getMapper(TicketMapper.class);
    @Mapping(source = "id", target = "ticketId")
    TicketDTO toDto(Ticket ticket);
    @Mapping(source = "ticketId", target = "id")
    Ticket toEntity(TicketDTO ticketDTO);

}
