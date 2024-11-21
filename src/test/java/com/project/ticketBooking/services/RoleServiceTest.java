package com.project.ticketBooking.services;

import com.project.ticketBooking.models.Role;
import com.project.ticketBooking.repositories.OrganizationRepository;
import com.project.ticketBooking.repositories.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.reactivestreams.Publisher;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static reactor.core.publisher.Mono.when;

public class RoleServiceTest {
    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllRoles() {
        Role role1 = new Role();
        role1.setId(1L);
        role1.setName("Admin");

        Role role2 = new Role();
        role2.setId(2L);
        role2.setName("User");

        List<Role> roles = Arrays.asList(role1, role2);

        when((Publisher<?>) roleRepository.findAll()).thenReturn(roles);

        List<Role> result = roleService.getAllRoles();

        assertEquals(2, result.size());
        assertEquals("Admin", result.get(0).getName());
        assertEquals("User", result.get(1).getName());
    }
}
