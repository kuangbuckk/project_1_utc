package com.project.ticketBooking.services.interfaces;

import com.project.ticketBooking.dtos.TicketDTO;
import com.project.ticketBooking.exceptions.DataNotFoundException;
import com.project.ticketBooking.models.Ticket;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ITicketService {
    List<Ticket> getAllTickets();
    List<Ticket> getTicketsByTicketCategoryId(Long ticketCategoryIds);
    Ticket getTicketById(Long id) throws DataNotFoundException;
    Ticket createTicket(TicketDTO ticketDTO) throws Exception;
    Ticket updateTicket(Long ticketId, TicketDTO ticketDTO) throws DataNotFoundException;
    Ticket updateTicketStatus(Long ticketId, String status) throws DataNotFoundException;
    void deleteTicket(Long ticketId);
    List<Ticket> getTicketByUserId(Long userId) throws DataNotFoundException;
    List<Ticket> getTicketsByTicketOrderDetailId(Long ticketOrderDetailId) throws DataNotFoundException;
    List<Ticket> getTicketsByOrganization(String token) throws DataNotFoundException;
//    List<Ticket> findAllPurchasedTickets();
    int getRemainingTicketsByCategoryId(Long ticketCategoryId);
}
