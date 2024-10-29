package com.project.ticketBooking.repositories;

import com.project.ticketBooking.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByTicketCategoryId(Long ticketCategoryId);
}
