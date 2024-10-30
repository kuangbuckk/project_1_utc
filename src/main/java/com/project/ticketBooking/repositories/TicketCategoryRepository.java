package com.project.ticketBooking.repositories;

import com.project.ticketBooking.models.TicketCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketCategoryRepository extends JpaRepository<TicketCategory, Long> {
    List<TicketCategory> findByEventId(Long eventId);
    boolean existsByIdAndRemainingCountGreaterThan(Long categoryId, int count);
}
