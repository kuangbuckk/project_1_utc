package com.project.ticketBooking.controllers;

import com.project.ticketBooking.dtos.TicketCategoryDTO;
import com.project.ticketBooking.models.TicketCategory;
import com.project.ticketBooking.responses.TicketCategoryResponse;
import com.project.ticketBooking.services.interfaces.ITicketCategoryService;
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
            return ResponseEntity.ok(TicketCategoryResponse.fromTicketCategory(ticketCategory));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/by-ids")
    public ResponseEntity<?> getTicketCategoriesByIds(
            @RequestParam("ids") String ids
    ) {
        try {
            List<Long> ticketCategoryIds = Arrays.stream(ids.split(","))
                    .map(Long::parseLong)
                    .toList();
            List<TicketCategory> ticketCategories = ticketCategoryService.getTicketCategoriesByIds(ticketCategoryIds);
            List<TicketCategoryResponse> ticketCategoriesResponses = ticketCategories.stream()
                    .map(TicketCategoryResponse::fromTicketCategory)
                    .toList();
            return ResponseEntity.ok(ticketCategoriesResponses);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
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

    @PostMapping("/organization")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_ORGANIZER')")
    public ResponseEntity<?> createTicketCategoryByOrganization(
            @RequestHeader("Authorization") String token,
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
            String authenticationOrganizationToken = token.substring(7);
            TicketCategory newTicketCategory = ticketCategoryService.createTicketCategoryFromOrganization(authenticationOrganizationToken, ticketCategoryDTO);
            return ResponseEntity.ok(newTicketCategory);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PutMapping("/organization/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_ORGANIZER')")
    public ResponseEntity<?> updateTicketCategoryByOrganization(
            @RequestHeader("Authorization") String token,
            @PathVariable("id") Long ticketCategoryId,
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
            String authenticationOrganizationToken = token.substring(7);
            TicketCategory updatedTicketCategory = ticketCategoryService
                    .updateTicketCategoryFromOrganization(
                            authenticationOrganizationToken,
                            ticketCategoryId,
                            ticketCategoryDTO
                    );
            return ResponseEntity.ok(updatedTicketCategory);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateTicketCategory(
            @PathVariable("id") Long ticketCategoryId,
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
            TicketCategory updatedTicketCategory = ticketCategoryService.updateTicketCategory(ticketCategoryId, ticketCategoryDTO);
            return ResponseEntity.ok(updatedTicketCategory);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_ORGANIZATION')")
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