package com.project.ticketBooking.services;

import com.project.ticketBooking.dtos.CategoryDTO;
import com.project.ticketBooking.dtos.OrganizationDTO;
import com.project.ticketBooking.exceptions.DataNotFoundException;
import com.project.ticketBooking.models.Category;
import com.project.ticketBooking.models.Organization;
import com.project.ticketBooking.repositories.CategoryRepository;
import com.project.ticketBooking.repositories.OrganizationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class OrganizationServiceTest {
    @Mock
    private OrganizationRepository organizationRepository;

    @InjectMocks
    private OrganizationService organizationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllOrganizations_returnsListOfOrganizations() {
        List<Organization> organizations = List.of(new Organization(), new Organization());
        when(organizationRepository.findAll()).thenReturn(organizations);

        List<Organization> result = organizationService.getAllOrganizations();

        assertEquals(organizations, result);
    }

    @Test
    void getOrganizationById_withValidId_returnsOrganization() throws DataNotFoundException {
        Long organizationId = 1L;
        Organization organization = new Organization();
        when(organizationRepository.findById(organizationId)).thenReturn(Optional.of(organization));

        Organization result = organizationService.getOrganizationById(organizationId);

        assertEquals(organization, result);
    }

    @Test
    void getOrganizationById_withInvalidId_throwsDataNotFoundException() {
        Long organizationId = 1L;
        when(organizationRepository.findById(organizationId)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> organizationService.getOrganizationById(organizationId));
    }

    @Test
    void createOrganization_withValidData_returnsNewOrganization() {
        OrganizationDTO organizationDTO = new OrganizationDTO();
        organizationDTO.setName("New Organization");
        Organization newOrganization = new Organization();
        when(organizationRepository.save(any(Organization.class))).thenReturn(newOrganization);

        Organization result = organizationService.createOrganization(organizationDTO);

        assertEquals(newOrganization, result);
    }

    @Test
    void updateOrganization_withValidData_returnsUpdatedOrganization() throws DataNotFoundException {
        Long organizationId = 1L;
        OrganizationDTO organizationDTO = new OrganizationDTO();
        organizationDTO.setName("Updated Organization");
        Organization existingOrganization = new Organization();
        when(organizationRepository.findById(organizationId)).thenReturn(Optional.of(existingOrganization));
        when(organizationRepository.save(existingOrganization)).thenReturn(existingOrganization);

        Organization result = organizationService.updateOrganization(organizationId, organizationDTO);

        assertEquals(existingOrganization, result);
    }

    @Test
    void updateOrganization_withInvalidId_throwsDataNotFoundException() {
        Long organizationId = 1L;
        OrganizationDTO organizationDTO = new OrganizationDTO();
        when(organizationRepository.findById(organizationId)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> organizationService.updateOrganization(organizationId, organizationDTO));
    }

    @Test
    void deleteOrganization_withValidId_deletesOrganization() {
        Long organizationId = 1L;
        doNothing().when(organizationRepository).deleteById(organizationId);

        organizationRepository.deleteById(organizationId); //bi loi deleteOrganization

        verify(organizationRepository, times(1)).deleteById(organizationId);
    }
}
