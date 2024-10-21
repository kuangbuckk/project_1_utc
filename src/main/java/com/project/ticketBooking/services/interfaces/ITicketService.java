package com.project.ticketBooking.services.interfaces;

import com.project.ticketBooking.dtos.EventDTO;
import com.project.ticketBooking.dtos.TicketDTO;
import com.project.ticketBooking.exceptions.DataNotFoundException;
import com.project.ticketBooking.models.Event;
import com.project.ticketBooking.models.Ticket;

import java.util.List;

public interface ITicketService {
    List<Ticket> getAllTicketsByEvent(Long eventId) throws DataNotFoundException; //add paging later
    Event getEventById(Long ticketId) throws DataNotFoundException;
    Event createTicket(TicketDTO ticketDTO) throws DataNotFoundException;
    Event updateTicket(Long ticketId, TicketDTO ticketDTO) throws DataNotFoundException;
    void deleteTicket(Long ticketId);
}
