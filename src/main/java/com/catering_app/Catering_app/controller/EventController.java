package com.catering_app.Catering_app.controller;

import com.catering_app.Catering_app.dto.EventDto;
import com.catering_app.Catering_app.dto.ResponseDto;
import com.catering_app.Catering_app.model.Events;
import com.catering_app.Catering_app.service.eventService.EventService;
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
    @DeleteMapping("/delete-event")
    public ResponseEntity<ResponseDto>deleteEvent(@RequestParam Integer id){
         eventService.deleteEvent(id);
        return ResponseEntity.ok(new ResponseDto(true,"delete success"));
    }
}
