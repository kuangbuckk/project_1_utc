package com.project.ticketBooking.services;

import com.project.ticketBooking.dtos.OrganizationDTO;
import com.project.ticketBooking.exceptions.DataNotFoundException;
import com.project.ticketBooking.models.Organization;
import com.project.ticketBooking.repositories.OrganizationRepository;
import com.project.ticketBooking.services.interfaces.IOrganizationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrganizationService implements IOrganizationService {
    private final OrganizationRepository organizationRepository;
    @Override
    public List<Organization> getAllOrganizations() {
        return organizationRepository.findAll();
    }

    @Override
    public Organization getOrganizationById(Long organizationId) throws DataNotFoundException {
        return organizationRepository
                .findById(organizationId)
                .orElseThrow(()-> new DataNotFoundException("Can't find organization with ID: " + organizationId));
    }

    @Override
    public Organization createOrganization(OrganizationDTO organizationDTO) {
        Organization newOrganization = Organization.builder()
                .name(organizationDTO.getName())
                .build();
        return organizationRepository.save(newOrganization);
    }

    @Override
    public Organization updateOrganization(Long organizationId, OrganizationDTO organizationDTO) throws DataNotFoundException {
        Organization exisitingOrganization = getOrganizationById(organizationId);
        exisitingOrganization.setName(organizationDTO.getName());
        return organizationRepository.save(exisitingOrganization);
    }

    @Override
    public void deleteOrganization(Long organizationId) {
        organizationRepository.deleteById(organizationId);
    }
}
