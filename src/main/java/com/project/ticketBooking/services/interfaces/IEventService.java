package com.project.ticketBooking.services.interfaces;

import com.project.ticketBooking.dtos.EventDTO;
import com.project.ticketBooking.dtos.EventImageDTO;
import com.project.ticketBooking.exceptions.DataNotFoundException;
import com.project.ticketBooking.exceptions.InvalidParamException;
import com.project.ticketBooking.exceptions.PermissionDenyException;
import com.project.ticketBooking.models.Event;
import com.project.ticketBooking.models.EventImage;
import com.project.ticketBooking.responses.EventResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface IEventService {
    List<EventResponse> getAllEvents();
    Page<EventResponse> getAllEventsPageable(PageRequest pageRequest); //add paging later
    Event getEventById(Long eventId) throws DataNotFoundException;
    Event createEvent(EventDTO eventDTO) throws DataNotFoundException, PermissionDenyException;
    Event updateEvent(Long eventId, EventDTO eventDTO) throws DataNotFoundException;
    void deleteEvent(Long eventId);
    Event updateEventStatus(Long eventId, String status) throws DataNotFoundException, InvalidParamException;
    EventImage createEventImage(Long eventId, EventImageDTO eventImageDTO) throws Exception;
    Page<EventResponse> getAllEventsByOrganizationId(Long organizationId, PageRequest pageRequest) throws DataNotFoundException;
    Page<EventResponse> getAllEventsByCategoryId(Long categoryId, PageRequest pageRequest) throws DataNotFoundException;
    Page<EventResponse> searchByKeyword(String keyword, PageRequest pageRequest);
}
