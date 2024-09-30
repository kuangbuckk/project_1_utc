package com.project.ticketBooking.controllers;

import com.project.ticketBooking.dtos.OrganizationDTO;
import com.project.ticketBooking.models.Organization;
import com.project.ticketBooking.services.OrganizationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/organizations")
@AllArgsConstructor
public class OrganizationController {
    private final OrganizationService organizationService;

    @GetMapping("")
    public ResponseEntity<?> getAllOrganizations(){
        List<Organization> organizations = organizationService.getAllOrganizations();
        return ResponseEntity.ok(organizations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrganizationById(
            @Valid @PathVariable("id") Long organizationId
    ) {
        try {
            Organization exisitingOrganization = organizationService.getOrganizationById(organizationId);
            return ResponseEntity.ok(exisitingOrganization);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("")
    public ResponseEntity<?> insertOrganization(
            @Valid @RequestBody OrganizationDTO organizationDTO,
            BindingResult result
    ){
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            Organization newOrganization = organizationService.createOrganization(organizationDTO);
            return ResponseEntity.ok(newOrganization);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrganization(
            @Valid @PathVariable("id") Long organizationId,
            @Valid @RequestBody OrganizationDTO organizationDTO
    ){
        try {
            Organization newOrganization = organizationService.updateOrganization(organizationId, organizationDTO);
            return ResponseEntity.ok(newOrganization);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrganization(
            @Valid @PathVariable("id") Long organizationId
    ){
        organizationService.deleteOrganization(organizationId);
        return ResponseEntity.ok("Organization deleted with ID: " + organizationId);
    }
}
