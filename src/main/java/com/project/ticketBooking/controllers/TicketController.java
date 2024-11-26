// src/main/java/com/project/ticketBooking/controllers/TicketController.java
package com.project.ticketBooking.controllers;

import com.project.ticketBooking.dtos.TicketDTO;
import com.project.ticketBooking.exceptions.DataNotFoundException;
import com.project.ticketBooking.models.Ticket;
import com.project.ticketBooking.responses.TicketResponse;
import com.project.ticketBooking.services.interfaces.ITicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final ITicketService ticketService;

    @GetMapping("/admin/retrieveAll")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAllTickets() {
        List<Ticket> tickets = ticketService.getAllTickets();
        List<TicketResponse> ticketResponses = Arrays.asList(tickets.stream()
                .map(TicketResponse::fromTicket)
                .toArray(TicketResponse[]::new));
        return ResponseEntity.ok(ticketResponses);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Ticket> getTicketById(@PathVariable Long id) throws DataNotFoundException {
        Ticket ticket = ticketService.getTicketById(id);
        return ResponseEntity.ok(ticket);
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
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

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_ORGANIZER')")
    public ResponseEntity<?> updateTicketStatus(
            @PathVariable Long id,
            @RequestParam String status
    ) {
        try {
            Ticket updatedTicket = ticketService.updateTicketStatus(id, status);
            return ResponseEntity.ok(updatedTicket);
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_ORGANIZER')")
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
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_ORGANIZER')")
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
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getTicketsByUserId(@PathVariable Long userId) {
        try {
            List<Ticket> tickets = ticketService.getTicketByUserId(userId);
            return ResponseEntity.ok(tickets);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping("/ticketCategory/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_ORGANIZER')")
    public ResponseEntity<?> getTicketsByTicketCategoryId(@PathVariable Long id) {
        try {
            List<Ticket> tickets = ticketService.getTicketsByTicketCategoryId(id);
            List<TicketResponse> ticketResponses = Arrays.asList(tickets.stream()
                    .map(TicketResponse::fromTicket)
                    .toArray(TicketResponse[]::new));
            return ResponseEntity.ok(ticketResponses);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/ticketOrderDetail/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_ORGANIZER') or hasRole('ROLE_USER')")
    public ResponseEntity<?> getTicketsByTicketOrderDetailId(@PathVariable Long id) {
        try {
            List<Ticket> tickets = ticketService.getTicketsByTicketOrderDetailId(id);
            List<TicketResponse> ticketResponses = Arrays.asList(tickets.stream()
                    .map(TicketResponse::fromTicket)
                    .toArray(TicketResponse[]::new));
            return ResponseEntity.ok(ticketResponses);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/organization/retrieveAll")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_ORGANIZER')")
    public ResponseEntity<?> getTicketsByOrganizationId(
            @RequestHeader("Authorization") String token
    ) {
        try {
            String extractedToken = token.substring(7);
            List<Ticket> tickets = ticketService.getTicketsByOrganization(extractedToken);
            List<TicketResponse> ticketResponses = Arrays.asList(tickets.stream()
                    .map(TicketResponse::fromTicket)
                    .toArray(TicketResponse[]::new));
            return ResponseEntity.ok(ticketResponses);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}