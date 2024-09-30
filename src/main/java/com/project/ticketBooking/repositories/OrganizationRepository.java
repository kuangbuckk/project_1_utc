package com.project.ticketBooking.repositories;

import com.project.ticketBooking.models.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
}
