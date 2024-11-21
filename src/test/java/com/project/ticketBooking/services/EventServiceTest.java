package com.project.ticketBooking.services;

import com.project.ticketBooking.dtos.CategoryDTO;
import com.project.ticketBooking.dtos.EventDTO;
import com.project.ticketBooking.exceptions.DataNotFoundException;
import com.project.ticketBooking.models.Category;
import com.project.ticketBooking.models.Event;
import com.project.ticketBooking.repositories.CategoryRepository;
import com.project.ticketBooking.repositories.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class EventServiceTest {
    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllEvents_returnsListOfEvents() {
        List<Event> events = List.of(new Event(), new Event());
        when(eventRepository.findAll()).thenReturn(events);

        List<Event> result = eventService.getAllEvents();

        assertEquals(events, result);
    }

    @Test
    void getEventById_withValidId_returnsEvent() throws DataNotFoundException {
        Long eventId = 1L;
        Event event = new Event();
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        Event result = eventService.getEventById(eventId);

        assertEquals(event, result);
    }

    @Test
    void getEventById_withInvalidId_throwsDataNotFoundException() {
        Long eventId = 1L;
        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> eventService.getEventById(eventId));
    }

    @Test
    void createEvent_withValidData_returnsNewEvent() throws DataNotFoundException {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setName("New Event");
        Event newEvent = new Event();
        when(eventRepository.save(any(Event.class))).thenReturn(newEvent);

        Event result = eventService.createEvent(eventDTO);

        assertEquals(newEvent, result);
    }

    @Test
    void updateEvent_withValidData_returnsUpdatedEvent() throws DataNotFoundException {
        Long eventId = 1L;
        EventDTO eventDTO = new EventDTO();
        eventDTO.setName("Updated Category");
        Event existingEvent = new Event();
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(existingEvent));
        when(eventRepository.save(existingEvent)).thenReturn(existingEvent);

        Event result = eventService.updateEvent(eventId, eventDTO);

        assertEquals(existingEvent, result);
    }

    @Test
    void updateEvent_withInvalidId_throwsDataNotFoundException() {
        Long eventId = 1L;
        EventDTO eventDTO = new EventDTO();
        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> eventService.updateEvent(eventId, eventDTO));
    }

    @Test
    void deleteEvent_withValidId_deletesEvent() {
        Long eventId = 1L;
        doNothing().when(eventRepository).deleteById(eventId);

        eventService.deleteEvent(eventId);

        verify(eventRepository, times(1)).deleteById(eventId);
    }
}
