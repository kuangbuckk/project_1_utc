package com.project.ticketBooking.services.interfaces;

import com.project.ticketBooking.dtos.EventDTO;
import com.project.ticketBooking.dtos.EventImageDTO;
import com.project.ticketBooking.exceptions.DataNotFoundException;
import com.project.ticketBooking.exceptions.InvalidParamException;
import com.project.ticketBooking.models.Event;
import com.project.ticketBooking.models.EventImage;
import com.project.ticketBooking.responses.EventResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface IEventService {
    Page<EventResponse> getAllEvents(PageRequest pageRequest); //add paging later
    Event getEventById(Long eventId) throws DataNotFoundException;
    Event createEvent(EventDTO eventDTO) throws DataNotFoundException;
    Event updateEvent(Long eventId, EventDTO eventDTO) throws DataNotFoundException;
    void deleteEvent(Long eventId);

    EventImage createEventImage(Long eventId, EventImageDTO eventImageDTO) throws Exception;
    List<Event> getAllEventsByOrganizationId(Long organizationId) throws DataNotFoundException;
    List<Event> getAllEventsByCategoryId(Long categoryId) throws DataNotFoundException;
}
