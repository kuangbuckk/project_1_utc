package com.project.ticketBooking.controllers;

import com.project.ticketBooking.models.Role;
import com.project.ticketBooking.services.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class RoleControllerTest {

    @Mock
    private RoleService roleService;

    @InjectMocks
    private RoleController roleController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllRoles_returnsListOfRoles() {
        List<Role> roles = List.of(new Role(), new Role());
        when(roleService.getAllRoles()).thenReturn(roles);

        ResponseEntity<?> response = roleController.getAllRoles();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(roles, response.getBody());
    }

    @Test
    void getAllRoles_whenNoRolesExist_returnsEmptyList() {
        when(roleService.getAllRoles()).thenReturn(List.of());

        ResponseEntity<?> response = roleController.getAllRoles();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(List.of(), response.getBody());
    }
}