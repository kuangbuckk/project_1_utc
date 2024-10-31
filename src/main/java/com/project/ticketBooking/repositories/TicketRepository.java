package com.project.ticketBooking.repositories;

import com.project.ticketBooking.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByTicketCategoryId(Long ticketCategoryId);
    List<Ticket> findAllByUserId(Long userId);
    @Query("SELECT t.remainingCount FROM TicketCategory t WHERE t.id = :ticketCategoryId")
    int getRemainingTicketsByTicketCategoryId(Long ticketCategoryId);
}
