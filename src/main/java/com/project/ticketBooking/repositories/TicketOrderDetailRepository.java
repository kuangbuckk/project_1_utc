package com.project.ticketBooking.repositories;

import com.project.ticketBooking.models.TicketOrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketOrderDetailRepository extends JpaRepository<TicketOrderDetail, Long> {
    List<TicketOrderDetail> findByTicketOrderId(Long ticketOrderId);
}
