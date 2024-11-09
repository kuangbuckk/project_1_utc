package com.project.ticketBooking.models;

import com.project.ticketBooking.services.interfaces.IEventRedisService;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@AllArgsConstructor
public class EventListener {
    private final IEventRedisService eventRedisService;
    private static final Logger logger = LoggerFactory.getLogger(EventListener.class);

    @PrePersist
    public void postPersist(Event event) {
        logger.info("Event with id {} is created", event.getId());
        eventRedisService.clear();
    }

    @PreUpdate
    public void preUpdate(Event event) {
        logger.info("Event with id {} is going to be updated", event.getId());
//        eventRedisService.clear();
    }

    @PostUpdate
    public void postUpdate(Event event) {
        logger.info("Event with id {} is updated", event.getId());
        eventRedisService.clear();
    }

    @PreRemove
    public void preRemove(Event event) {
        logger.info("Event with id {} is going to be deleted", event.getId());
    }

    @PostRemove
    public void postRemove(Event event) {
        logger.info("Event with id {} is deleted", event.getId());
        eventRedisService.clear();
    }
}
