package com.catering_app.Catering_app.service.eventService;

import com.catering_app.Catering_app.dto.EventDto;
import com.catering_app.Catering_app.model.Events;

import java.util.List;
import java.util.Optional;

public interface EventService {

    boolean addEvent(EventDto eventDto);
    List<Events> getAllEvents();
    Optional<Events>findByEventName(String eventName);
    void deleteEvent(Integer id);
    Optional<Events> getEventById(Integer id);
}
