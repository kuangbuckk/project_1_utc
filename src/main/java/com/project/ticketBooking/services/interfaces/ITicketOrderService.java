package com.project.ticketBooking.services.interfaces;

import com.project.ticketBooking.dtos.TicketOrderDTO;
import com.project.ticketBooking.exceptions.DataNotFoundException;
import com.project.ticketBooking.models.TicketOrder;

import java.util.List;

public interface ITicketOrderService {
    TicketOrder getTicketOrderById(Long id) throws DataNotFoundException;
    TicketOrder createTicketOrder(TicketOrderDTO ticketOrderDTO) throws DataNotFoundException;
    TicketOrder updateTicketOrder(Long id, TicketOrderDTO ticketOrderDTO) throws DataNotFoundException;
    void deleteTicketOrder(Long id);
    List<TicketOrder> getTicketOrdersByUserId(Long userId);
}
