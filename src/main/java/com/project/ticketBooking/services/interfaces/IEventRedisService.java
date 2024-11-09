package com.project.ticketBooking.services.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.ticketBooking.responses.EventResponse;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface IEventRedisService {
    void clear(); //clear cache when new data
    List<EventResponse> getAllEvents(PageRequest pageRequest) throws JsonProcessingException;
    void saveAllEvents(List<EventResponse> eventResponses, PageRequest pageRequest) throws JsonProcessingException;
}
