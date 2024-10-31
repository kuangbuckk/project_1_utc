package com.project.ticketBooking.services;

import com.project.ticketBooking.models.Role;
import com.project.ticketBooking.repositories.RoleRepository;
import com.project.ticketBooking.services.interfaces.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService {
    private final RoleRepository roleRepository;
    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}