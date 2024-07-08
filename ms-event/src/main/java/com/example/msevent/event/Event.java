package com.example.msevent.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document(collection = "event")
public class Event {
    @Id
    private String id;
    private String eventPlace;
    private String eventDate;

}

