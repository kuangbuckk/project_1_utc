package com.project.ticketBooking.repositories;

import com.project.ticketBooking.models.TicketOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketOrderRepository extends JpaRepository<TicketOrder, Long> {
    List<TicketOrder> findByUserId(Long userId);
}
