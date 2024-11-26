package com.project.ticketBooking.services;

import com.project.ticketBooking.dtos.EventDTO;
import com.project.ticketBooking.dtos.EventImageDTO;
import com.project.ticketBooking.exceptions.DataNotFoundException;
import com.project.ticketBooking.exceptions.InvalidParamException;
import com.project.ticketBooking.exceptions.PermissionDenyException;
import com.project.ticketBooking.models.*;
import com.project.ticketBooking.repositories.CategoryRepository;
import com.project.ticketBooking.repositories.EventImageRepository;
import com.project.ticketBooking.repositories.EventRepository;
import com.project.ticketBooking.repositories.OrganizationRepository;
import com.project.ticketBooking.responses.EventResponse;
import com.project.ticketBooking.services.interfaces.IEventService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public List<EventResponse> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        return events.stream()
                .map(EventResponse::fromEvent)
                .toList();
    }

    @Override
    public Page<EventResponse> getAllEventsPageable(PageRequest pageRequest) {
        return eventRepository.findAll(pageRequest)
                .map(EventResponse::fromEvent);
    }

    @Override
    public Event getEventById(Long eventId) throws DataNotFoundException {
        return eventRepository
                .findById(eventId)
                .orElseThrow(()-> new DataNotFoundException("Can't find event with ID: " + eventId));
    }

    @Override
    public Event createEvent(EventDTO eventDTO) throws DataNotFoundException, PermissionDenyException {
        if (eventDTO.getStatus() != null) {
            throw new PermissionDenyException("You have to wait for admin to approve your event");
        }
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
                .status(EventStatus.PENDING) //default status xong r admin se duyet sang active
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
//        exisitingEvent.setStatus(eventDTO.getStatus());
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
    public Event updateEventStatus(Long eventId, String status) throws DataNotFoundException, InvalidParamException {
        Event existingEvent = eventRepository
                .findById(eventId)
                .orElseThrow(() ->
                        new DataNotFoundException(
                                "Cannot find event with id: "+ eventId));
        if (status.equals(EventStatus.PENDING) ||
                status.equals(EventStatus.ACTIVE) ||
                status.equals(EventStatus.CANCELLED) ||
                status.equals(EventStatus.COMPLETED)
        ) {
            existingEvent.setStatus(status);
            return eventRepository.save(existingEvent);
        } else {
            throw new InvalidParamException("Invalid status");
        }

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

        //ảnh đầu tiên sẽ làm thumbbnail
        if(size == 0) {
            existingEvent.setThumbnail(eventImageDTO.getImageUrl());
            eventRepository.save(existingEvent);
        }
        if(size >= EventImage.MAXIMUM_IMAGES_PER_EVENT) {
            throw new InvalidParamException(
                    "Number of images must be <= "
                            + EventImage.MAXIMUM_IMAGES_PER_EVENT);
        }
        return eventImageRepository.save(newEventImage);
    }

    @Override
    public Page<EventResponse> getAllEventsByOrganizationId(Long organizationId, PageRequest pageRequest) throws DataNotFoundException {
        return eventRepository.findByOrganizationId(organizationId, pageRequest).map(EventResponse::fromEvent);
    }

    @Override
    public List<EventResponse> getAllEventsByOrganization(Long organizationId) {
        return eventRepository.findAllByOrganizationId(organizationId)
                .stream()
                .map(EventResponse::fromEvent)
                .toList();
    }

    @Override
    public Page<EventResponse> getAllEventsByCategoryId(Long categoryId, PageRequest pageRequest) throws DataNotFoundException {
        return eventRepository.findByCategoryId(categoryId, pageRequest)
                .map(EventResponse::fromEvent);
    }

    @Override
    public Page<EventResponse> searchByKeyword(String keyword, PageRequest pageRequest) {
        return eventRepository.searchByKeyword(keyword, pageRequest)
                .map(EventResponse::fromEvent);
    }

    public long getTotalEventsCount() {
        return eventRepository.count();
    }
}
