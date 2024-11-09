package com.project.ticketBooking.repositories;

import com.project.ticketBooking.models.Ticket;
import com.project.ticketBooking.models.TicketCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TicketCategoryRepository extends JpaRepository<TicketCategory, Long> {
    List<TicketCategory> findByEventId(Long eventId);
    boolean existsByIdAndRemainingCountGreaterThan(Long categoryId, int count);

    @Query("SELECT tc FROM TicketCategory tc WHERE tc.id IN :ticketCategoryIds")
    List<TicketCategory> findTicketCategoriesByIds(@Param("ticketCategoryIds") List<Long> ticketCategoryIds);
}
