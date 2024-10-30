package com.project.ticketBooking.services.interfaces;

import com.project.ticketBooking.dtos.EventDTO;
import com.project.ticketBooking.dtos.EventImageDTO;
import com.project.ticketBooking.exceptions.DataNotFoundException;
import com.project.ticketBooking.exceptions.InvalidParamException;
import com.project.ticketBooking.models.Event;
import com.project.ticketBooking.models.EventImage;

import java.util.List;

public interface IEventService {
    List<Event> getAllEvents(); //add paging later
    Event getEventById(Long eventId) throws DataNotFoundException;
    Event createEvent(EventDTO eventDTO) throws DataNotFoundException;
    Event updateEvent(Long eventId, EventDTO eventDTO) throws DataNotFoundException;
    void deleteEvent(Long eventId);

    EventImage createEventImage(Long eventId, EventImageDTO eventImageDTO) throws Exception;
    List<Event> getAllEventsByOrganizationId(Long organizationId) throws DataNotFoundException;
}
