package com.project.ticketBooking.repositories;

import com.project.ticketBooking.models.TicketCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketCategoryRepository extends JpaRepository<TicketCategory, Long> {
}
