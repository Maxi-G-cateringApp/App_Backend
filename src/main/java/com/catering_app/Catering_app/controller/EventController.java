package com.catering_app.Catering_app.controller;

import com.catering_app.Catering_app.dto.EventDto;
import com.catering_app.Catering_app.dto.ResponseDto;
import com.catering_app.Catering_app.model.Events;
import com.catering_app.Catering_app.service.eventService.EventService;
import jakarta.persistence.EntityExistsException;
import jdk.jfr.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping("/add-event")
    public ResponseEntity<?> addEvent(@RequestBody EventDto eventDto){
        Optional<Events> event = eventService.findByEventName(eventDto.getEventName());
            if (event.isPresent()){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Data Exist");
            }else{
                return ResponseEntity.ok(eventService.addEvent(eventDto));
            }
        }
    @GetMapping("/events")
    public ResponseEntity<List<Events>> getAllEvents(){
        return ResponseEntity.ok(eventService.getAllEvents());
    }
    @GetMapping("/get/event")
    public ResponseEntity<Events> getEventById(@RequestParam Integer id){
        Optional<Events> optionalEvents = eventService.getEventById(id);
        if (optionalEvents.isPresent()){
            return ResponseEntity.ok(optionalEvents.get());
        }else{
            throw new EntityExistsException();
        }
    }

    @PutMapping("/edit/event")
    public ResponseEntity<ResponseDto>editEvent(@RequestParam Integer id,
                                                @RequestBody EventDto eventDto){
        boolean response = eventService.editEvent(id,eventDto);
        if (response){
            return ResponseEntity.ok(new ResponseDto(true,"success"));
        }else {
            return ResponseEntity.ok(new ResponseDto(false,"Wrong"));
        }
    }
    @DeleteMapping("/delete-event")
    public ResponseEntity<ResponseDto>deleteEvent(@RequestParam Integer id){
         eventService.deleteEvent(id);
        return ResponseEntity.ok(new ResponseDto(true,"delete success"));
    }
}
