package com.project.ticketBooking.services;

import com.project.ticketBooking.dtos.TicketDTO;
import com.project.ticketBooking.exceptions.DataNotFoundException;
import com.project.ticketBooking.models.Event;
import com.project.ticketBooking.models.Ticket;
import com.project.ticketBooking.repositories.EventRepository;
import com.project.ticketBooking.repositories.TicketRepository;
import com.project.ticketBooking.services.interfaces.ITicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService implements ITicketService {
    private final TicketRepository ticketRepository;
    private final EventRepository eventRepository;

    @Override
    public List<Ticket> getAllTicketsByEvent(Long eventId) throws DataNotFoundException {
        Event existingEvent = eventRepository.findById(eventId)
                .orElseThrow(()-> new DataNotFoundException("Can't find event with ID: " + eventId));
        List<Ticket> ticketList = existingEvent.
        return null;
    }

    @Override
    public Event getEventById(Long ticketId) throws DataNotFoundException {
        return null;
    }

    @Override
    public Event createTicket(TicketDTO ticketDTO) throws DataNotFoundException {
        return null;
    }

    @Override
    public Event updateTicket(Long ticketId, TicketDTO ticketDTO) throws DataNotFoundException {
        return null;
    }

    @Override
    public void deleteTicket(Long ticketId) {

    }
}
