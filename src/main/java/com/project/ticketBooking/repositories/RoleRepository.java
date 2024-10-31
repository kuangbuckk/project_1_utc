package com.project.ticketBooking.repositories;

import com.project.ticketBooking.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
