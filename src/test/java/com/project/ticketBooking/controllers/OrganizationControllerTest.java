package com.project.ticketBooking.controllers;

import com.project.ticketBooking.dtos.OrganizationDTO;
import com.project.ticketBooking.exceptions.DataNotFoundException;
import com.project.ticketBooking.models.Organization;
import com.project.ticketBooking.services.OrganizationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrganizationControllerTest {

    @Mock
    private OrganizationService organizationService;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private OrganizationController organizationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllOrganizations_returnsListOfOrganizations() {
        List<Organization> organizations = List.of(new Organization(), new Organization());
        when(organizationService.getAllOrganizations()).thenReturn(organizations);

        ResponseEntity<?> response = organizationController.getAllOrganizations();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(organizations, response.getBody());
    }

    @Test
    void getOrganizationById_withValidId_returnsOrganization() throws DataNotFoundException {
        Long organizationId = 1L;
        Organization organization = new Organization();
        when(organizationService.getOrganizationById(organizationId)).thenReturn(organization);

        ResponseEntity<?> response = organizationController.getOrganizationById(organizationId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(organization, response.getBody());
    }

    @Test
    void getOrganizationById_withInvalidId_returnsErrorMessage() throws DataNotFoundException {
        Long organizationId = 1L;
        when(organizationService.getOrganizationById(organizationId)).thenThrow(new DataNotFoundException("Organization not found"));

        ResponseEntity<?> response = organizationController.getOrganizationById(organizationId);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Organization not found", response.getBody());
    }

    @Test
    void insertOrganization_withValidData_returnsNewOrganization() {
        OrganizationDTO organizationDTO = new OrganizationDTO();
        Organization newOrganization = new Organization();
        when(organizationService.createOrganization(organizationDTO)).thenReturn(newOrganization);
        when(bindingResult.hasErrors()).thenReturn(false);

        ResponseEntity<?> response = organizationController.insertOrganization(organizationDTO, bindingResult);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(newOrganization, response.getBody());
    }

    @Test
    void insertOrganization_withInvalidData_returnsErrorMessages() {
        OrganizationDTO organizationDTO = new OrganizationDTO();
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(new FieldError("organizationDTO", "name", "Name is required")));

        ResponseEntity<?> response = organizationController.insertOrganization(organizationDTO, bindingResult);

        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof List);
        assertEquals("Name is required", ((List<?>) response.getBody()).get(0));
    }

    @Test
    void updateOrganization_withValidData_returnsUpdatedOrganization() throws DataNotFoundException {
        Long organizationId = 1L;
        OrganizationDTO organizationDTO = new OrganizationDTO();
        Organization updatedOrganization = new Organization();
        when(organizationService.updateOrganization(organizationId, organizationDTO)).thenReturn(updatedOrganization);

        ResponseEntity<?> response = organizationController.updateOrganization(organizationId, organizationDTO, bindingResult);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(updatedOrganization, response.getBody());
    }

    @Test
    void updateOrganization_withInvalidId_returnsErrorMessage() throws DataNotFoundException {
        Long organizationId = 1L;
        OrganizationDTO organizationDTO = new OrganizationDTO();
        when(organizationService.updateOrganization(organizationId, organizationDTO)).thenThrow(new DataNotFoundException("Organization not found"));

        ResponseEntity<?> response = organizationController.updateOrganization(organizationId, organizationDTO, bindingResult);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Organization not found", response.getBody());
    }

    @Test
    void deleteOrganization_withValidId_returnsSuccessMessage() {
        Long organizationId = 1L;
        doNothing().when(organizationService).deleteOrganization(organizationId);

        ResponseEntity<?> response = organizationController.deleteOrganization(organizationId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Organization deleted with ID: " + organizationId, response.getBody());
    }
}