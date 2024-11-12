// src/main/java/com/project/ticketBooking/controllers/TicketOrderController.java
package com.project.ticketBooking.controllers;

import com.project.ticketBooking.dtos.TicketOrderDTO;
import com.project.ticketBooking.exceptions.DataNotFoundException;
import com.project.ticketBooking.models.TicketOrder;
import com.project.ticketBooking.services.interfaces.ITicketOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/ticketOrders")
@RequiredArgsConstructor
public class TicketOrderController {
    private final ITicketOrderService ticketOrderService;

    @GetMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<TicketOrder>> getAllTicketOrders() {
        List<TicketOrder> ticketOrders = ticketOrderService.getTicketOrdersByUserId(null);
        return ResponseEntity.ok(ticketOrders);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<TicketOrder> getTicketOrderById(@PathVariable Long id) throws DataNotFoundException {
        TicketOrder ticketOrder = ticketOrderService.getTicketOrderById(id);
        return ResponseEntity.ok(ticketOrder);
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> createTicketOrder(
            @Valid @RequestBody TicketOrderDTO ticketOrderDTO,
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
            TicketOrder newTicketOrder = ticketOrderService.createTicketOrder(ticketOrderDTO);
            return ResponseEntity.ok(newTicketOrder);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateTicketOrder(
            @PathVariable("id") Long ticketOrderId,
            @Valid @RequestBody TicketOrderDTO ticketOrderDTO
    ) {
        try {
            TicketOrder updatedTicketOrder = ticketOrderService.updateTicketOrder(ticketOrderId, ticketOrderDTO);
            return ResponseEntity.ok(updatedTicketOrder);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteTicketOrder(
            @PathVariable("id") Long ticketOrderId
    ) {
        try {
            ticketOrderService.deleteTicketOrder(ticketOrderId);
            return ResponseEntity.ok("Deleted ticket order with ID: " + ticketOrderId);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getTicketOrdersByUserId(@PathVariable Long userId) {
        try {
            List<TicketOrder> ticketOrders = ticketOrderService.getTicketOrdersByUserId(userId);
            return ResponseEntity.ok(ticketOrders);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}