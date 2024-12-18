package com.project.ticketBooking.repositories;

import com.project.ticketBooking.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findAllByTicketCategoryId(Long ticketCategoryId);
    List<Ticket> findByTicketOrderDetailId(Long ticketOrderDetailId);

    List<Ticket> findAllByUserId(Long userId);
    @Query("SELECT t.remainingCount FROM TicketCategory t WHERE t.id = :ticketCategoryId")
    int getRemainingTicketsByTicketCategoryId(Long ticketCategoryId);


    @Query("SELECT t FROM Ticket t JOIN t.ticketCategory tc JOIN tc.event e JOIN e.organization o WHERE o.id = :organizationId")
    List<Ticket> findAllByOrganizationId(@Param("organizationId") Long organizationId);
}
