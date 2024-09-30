package com.project.ticketBooking.repositories;

import com.project.ticketBooking.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
