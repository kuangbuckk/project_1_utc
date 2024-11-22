package com.project.ticketBooking.services;

import com.project.ticketBooking.models.Category;
import com.project.ticketBooking.models.User;
import com.project.ticketBooking.repositories.RoleRepository;
import com.project.ticketBooking.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllUsers_returnsListOfUsers() {
        List<User> users = List.of(new User(), new User());
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userRepository.getAllCategories();

        assertEquals(categories, result);
    }
}