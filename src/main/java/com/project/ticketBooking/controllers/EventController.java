package com.project.ticketBooking.controllers;

import com.project.ticketBooking.dtos.EventDTO;
import com.project.ticketBooking.models.Event;
import com.project.ticketBooking.services.EventService;
import com.project.ticketBooking.services.EventService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/events")
@AllArgsConstructor
public class EventController {
    private final EventService eventService;

    @GetMapping("")
    public ResponseEntity<?> getAllEvents(){
        List<Event> Events = eventService.getAllEvents();
        return ResponseEntity.ok(Events);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEventById(
            @Valid @PathVariable("id") Long eventId
    ) {
        try {
            Event exisitingEvent = eventService.getEventById(eventId);
            return ResponseEntity.ok(exisitingEvent);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("")
    public ResponseEntity<?> insertEvent(
            @Valid @RequestBody EventDTO eventDTO,
            BindingResult result
    ){
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            Event newEvent = eventService.createEvent(eventDTO);
            return ResponseEntity.ok(newEvent);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEvent(
            @Valid @PathVariable("id") Long eventId,
            @Valid @RequestBody EventDTO eventDTO
    ){
        try {
            Event newEvent = eventService.updateEvent(eventId, eventDTO);
            return ResponseEntity.ok(newEvent);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvent(
            @Valid @PathVariable("id") Long eventId
    ){
        eventService.deleteEvent(eventId);
        return ResponseEntity.ok("Event deleted with ID: " + eventId);
    }
}
