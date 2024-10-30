package com.project.ticketBooking.repositories;

import com.project.ticketBooking.models.Event;
import com.project.ticketBooking.models.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByOrganizationId(Long organization_id);
}
