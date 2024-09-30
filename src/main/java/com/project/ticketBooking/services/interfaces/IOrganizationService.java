package com.project.ticketBooking.services.interfaces;

import com.project.ticketBooking.dtos.CategoryDTO;
import com.project.ticketBooking.dtos.OrganizationDTO;
import com.project.ticketBooking.exceptions.DataNotFoundException;
import com.project.ticketBooking.models.Category;
import com.project.ticketBooking.models.Organization;

import java.util.List;

public interface IOrganizationService {
    List<Organization> getAllOrganizations();
    Organization getOrganizationById(Long id) throws DataNotFoundException;
    Organization createOrganization(OrganizationDTO organizationDTO);
    Organization updateOrganization(Long organizationId, OrganizationDTO organizationDTO) throws DataNotFoundException;
    void deleteOrganization(Long organizationId);
}
