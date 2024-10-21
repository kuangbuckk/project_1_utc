package com.project.ticketBooking.repositories;

import com.project.ticketBooking.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
