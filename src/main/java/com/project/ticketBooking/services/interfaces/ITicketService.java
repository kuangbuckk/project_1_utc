package com.project.ticketBooking.services.interfaces;

import com.project.ticketBooking.dtos.EventDTO;
import com.project.ticketBooking.dtos.TicketDTO;
import com.project.ticketBooking.exceptions.DataNotFoundException;
import com.project.ticketBooking.models.Event;
import com.project.ticketBooking.models.Ticket;

import java.util.List;

public interface ITicketService {
    List<Ticket> getTicketsByTicketCategoryId(Long ticketCategoryId);
    Ticket getTicketById(Long id) throws DataNotFoundException;
    Ticket createTicket(TicketDTO ticketDTO);
    Ticket updateTicket(Long ticketId, TicketDTO ticketDTO) throws DataNotFoundException;
    void deleteTicket(Long ticketId);
}
