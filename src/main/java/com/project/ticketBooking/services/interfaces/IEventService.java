package com.project.ticketBooking.services.interfaces;

import com.project.ticketBooking.dtos.EventDTO;
import com.project.ticketBooking.exceptions.DataNotFoundException;
import com.project.ticketBooking.models.Event;

import java.util.List;

public interface IEventService {
    List<Event> getAllEvents(); //add paging later
    Event getEventById(Long eventId) throws DataNotFoundException;
    Event createEvent(EventDTO eventDTO) throws DataNotFoundException;
    Event updateEvent(Long eventId, EventDTO eventDTO) throws DataNotFoundException;
    void deleteEvent(Long eventId);
}
