package com.example.msevent.event;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EventMapper {
    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);
    @Mapping(target = "eventId", source = "id")
    EventDTO toDto(Event stock);
    @Mapping(target = "id", source = "eventId")
    Event toEntity(EventDTO eventDTO);
}
