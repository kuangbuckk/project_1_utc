// src/main/java/com/project/ticketBooking/controllers/TicketController.java
package com.project.ticketBooking.controllers;

import com.project.ticketBooking.dtos.TicketDTO;
import com.project.ticketBooking.exceptions.DataNotFoundException;
import com.project.ticketBooking.models.Ticket;
import com.project.ticketBooking.services.interfaces.ITicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final ITicketService ticketService;

//    @GetMapping("")
//    public ResponseEntity<List<Ticket>> getAllTickets() {
//        List<Ticket> tickets = ticketService.getTicketsByTicketCategoryId(null);
//        return ResponseEntity.ok(tickets);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable Long id) throws DataNotFoundException {
        Ticket ticket = ticketService.getTicketById(id);
        return ResponseEntity.ok(ticket);
    }

    @PostMapping("")
    public ResponseEntity<?> createTicket(
            @Valid @RequestBody TicketDTO ticketDTO,
            BindingResult result
    ) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            Ticket newTicket = ticketService.createTicket(ticketDTO);
            return ResponseEntity.ok(newTicket);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTicket(
            @PathVariable("id") Long ticketId,
            @Valid @RequestBody TicketDTO ticketDTO
    ) {
        try {
            Ticket updatedTicket = ticketService.updateTicket(ticketId, ticketDTO);
            return ResponseEntity.ok(updatedTicket);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTicket(
            @PathVariable("id") Long ticketId
    ) {
        try {
            ticketService.deleteTicket(ticketId);
            return ResponseEntity.ok("Deleted ticket with ID: " + ticketId);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getTicketsByUserId(@PathVariable Long userId) {
        try {
            List<Ticket> tickets = ticketService.getTicketByUserId(userId);
            return ResponseEntity.ok(tickets);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}