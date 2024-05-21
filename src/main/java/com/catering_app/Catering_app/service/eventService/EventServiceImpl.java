package com.catering_app.Catering_app.service.eventService;

import com.catering_app.Catering_app.dto.EventDto;
import com.catering_app.Catering_app.model.Events;
import com.catering_app.Catering_app.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService{

    @Autowired
    private EventRepository eventRepository;
    @Override
    public boolean addEvent(EventDto eventDto) {
        Events event = new Events();
        if (eventDto.getEventName().isEmpty()) return false;
        event.setEventName(eventDto.getEventName());
        eventRepository.save(event);
        return true;
    }

    @Override
    public List<Events> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public Optional<Events> findByEventName(String eventName) {
        return eventRepository.findByEventName(eventName);
    }

    @Override
    public void deleteEvent(Integer id) {
        eventRepository.deleteById(id);

    }

    @Override
    public Optional<Events> getEventById(Integer id) {
        return eventRepository.findById(id);
    }

    @Override
    public boolean editEvent(Integer id, EventDto eventDto) {
        Optional<Events> optionalEvents = eventRepository.findById(id);
        if (optionalEvents.isPresent()){
            Events event = optionalEvents.get();

            Optional<Events> existingEvent = eventRepository.findByEventName(eventDto.getEventName());
            if (existingEvent.isPresent() && ! existingEvent.get().getId().equals(id)){
                return false;
            }
            event.setEventName(eventDto.getEventName());
            eventRepository.save(event);
            return true;
        }
        return false;
    }
}
