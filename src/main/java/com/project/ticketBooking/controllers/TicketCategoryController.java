package com.project.ticketBooking.controllers;

import com.project.ticketBooking.dtos.TicketCategoryDTO;
import com.project.ticketBooking.models.TicketCategory;
import com.project.ticketBooking.responses.TicketCategoryResponse;
import com.project.ticketBooking.services.interfaces.ITicketCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/ticketCategories")
@RequiredArgsConstructor
public class TicketCategoryController {
    private final ITicketCategoryService ticketCategoryService;

    @GetMapping("")
    public ResponseEntity<List<TicketCategory>> getAllTicketCategories() {
        List<TicketCategory> ticketCategories = ticketCategoryService.getAllTicketCategories();
        return ResponseEntity.ok(ticketCategories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTicketCategoryById(
            @PathVariable("id") Long ticketCategoryId
    ) {
        try {
            TicketCategory ticketCategory = ticketCategoryService.getTicketCategoryById(ticketCategoryId);
            return ResponseEntity.ok(ticketCategory);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("")
    public ResponseEntity<?> createTicketCategory(
            @Valid @RequestBody TicketCategoryDTO ticketCategoryDTO,
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
            TicketCategory newTicketCategory = ticketCategoryService.createTicketCategory(ticketCategoryDTO);
            return ResponseEntity.ok(newTicketCategory);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTicketCategory(
            @PathVariable("id") Long ticketCategoryId,
            @Valid @RequestBody TicketCategoryDTO ticketCategoryDTO
    ) {
        try {
            TicketCategory updatedTicketCategory = ticketCategoryService.updateTicketCategory(ticketCategoryId, ticketCategoryDTO);
            return ResponseEntity.ok(updatedTicketCategory);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTicketCategory(
            @PathVariable("id") Long ticketCategoryId
    ) {
        try {
            ticketCategoryService.deleteTicketCategory(ticketCategoryId);
            return ResponseEntity.ok(ticketCategoryId);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping("events/{id}")
    public ResponseEntity<?> getTicketCategoriesByEventId(
            @PathVariable("id") Long eventId
    ) {
        try {
            List<TicketCategory> ticketCategories = ticketCategoryService.getTicketCategoriesByEventId(eventId);
            List<TicketCategoryResponse> ticketCategoriesResponses = ticketCategories.stream()
                    .map(TicketCategoryResponse::fromTicketCategory)
                    .toList();
            return ResponseEntity.ok(ticketCategoriesResponses);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}