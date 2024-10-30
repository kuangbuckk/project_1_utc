package com.project.ticketBooking.services;

import com.project.ticketBooking.dtos.EventDTO;
import com.project.ticketBooking.dtos.EventImageDTO;
import com.project.ticketBooking.exceptions.DataNotFoundException;
import com.project.ticketBooking.exceptions.InvalidParamException;
import com.project.ticketBooking.models.Category;
import com.project.ticketBooking.models.Event;
import com.project.ticketBooking.models.EventImage;
import com.project.ticketBooking.models.Organization;
import com.project.ticketBooking.repositories.CategoryRepository;
import com.project.ticketBooking.repositories.EventImageRepository;
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
    private final EventImageRepository eventImageRepository;
    private final OrganizationRepository organizationRepository;
    private final CategoryRepository categoryRepository;
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
                .orElseThrow(()-> new DataNotFoundException("Can't find organization with ID:" + eventDTO.getOrganizeId()));
        Category existingCategory = categoryRepository.findById(eventDTO.getCategoryId())
                .orElseThrow(()-> new DataNotFoundException("Can't find category with ID:" + eventDTO.getCategoryId()));
        Event newEvent = Event.builder()
                .name(eventDTO.getName())
                .description(eventDTO.getDescription())
                .location(eventDTO.getLocation())
                .startDate(eventDTO.getStartDate())
                .endDate(eventDTO.getEndDate())
                .category(existingCategory)
                .organization(existingOrganization)
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
        exisitingEvent.setStartDate(eventDTO.getStartDate());
        exisitingEvent.setEndDate(eventDTO.getEndDate());
        //Find category by id
        Category existingCategory = categoryRepository.findById(eventDTO.getCategoryId())
                .orElseThrow(()-> new DataNotFoundException("Can't find category with ID:" + eventDTO.getCategoryId()));
        exisitingEvent.setCategory(existingCategory);
        //exclude organization reference
        return eventRepository.save(exisitingEvent);
    }

    @Override
    public void deleteEvent(Long eventId) {
        eventRepository.deleteById(eventId);
    }

    @Override
    public EventImage createEventImage(Long eventId, EventImageDTO eventImageDTO) throws Exception {
        Event existingEvent = eventRepository
                .findById(eventId)
                .orElseThrow(() ->
                        new DataNotFoundException(
                                "Cannot find event with id: "+ eventImageDTO.getEventId()));
        EventImage newEventImage = EventImage.builder()
                .event(existingEvent)
                .imageUrl(eventImageDTO.getImageUrl())
                .build();
        //Ko cho insert quá 5 ảnh cho 1 sản phẩm
        int size = eventImageRepository.findByEventId(eventId).size();
        if(size >= EventImage.MAXIMUM_IMAGES_PER_EVENT) {
            throw new InvalidParamException(
                    "Number of images must be <= "
                            + EventImage.MAXIMUM_IMAGES_PER_EVENT);
        }
        return eventImageRepository.save(newEventImage);
    }

    @Override
    public List<Event> getAllEventsByOrganizationId(Long organizationId) throws DataNotFoundException {
        return eventRepository.findByOrganizationId(organizationId);
    }
}
