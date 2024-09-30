package com.project.ticketBooking.services;

import com.project.ticketBooking.dtos.EventDTO;
import com.project.ticketBooking.exceptions.DataNotFoundException;
import com.project.ticketBooking.models.Event;
import com.project.ticketBooking.models.Organization;
import com.project.ticketBooking.repositories.EventRepository;
import com.project.ticketBooking.repositories.OrganizationRepository;
import com.project.ticketBooking.services.interfaces.IEventService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class EventService implements IEventService {
    private final EventRepository eventRepository;
    private final OrganizationRepository organizationRepository;
    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public Event getEventById(Long eventId) throws DataNotFoundException {
        return eventRepository
                .findById(eventId)
                .orElseThrow(()-> new DataNotFoundException("Can't find event with ID: " + eventId));
    }

    @Override
    public Event createEvent(EventDTO eventDTO) throws DataNotFoundException {
        Organization existingOrganization = organizationRepository
                .findById(eventDTO.getOrganizeId())
                .orElseThrow(()-> new DataNotFoundException("Can't find organization with ID:"));
        Event newEvent = Event.builder()
                .name(eventDTO.getName())
                .description(eventDTO.getDescription())
                .location(eventDTO.getLocation())
                .organizationId(existingOrganization)
                .build();
        return eventRepository.save(newEvent);
    }

    @Override
    public Event updateEvent(Long eventId, EventDTO eventDTO) throws DataNotFoundException {
        Event exisitingEvent = eventRepository
                .findById(eventId)
                .orElseThrow(()-> new DataNotFoundException("Can't find event with ID: " + eventId));
        exisitingEvent.setName(eventDTO.getName());
        exisitingEvent.setDescription(eventDTO.getDescription());
        exisitingEvent.setLocation(eventDTO.getLocation());
        //exclude organization reference
        return eventRepository.save(exisitingEvent);
    }

    @Override
    public void deleteEvent(Long eventId) {
        eventRepository.deleteById(eventId);
    }
}
