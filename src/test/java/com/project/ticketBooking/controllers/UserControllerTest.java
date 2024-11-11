package com.project.ticketBooking.controllers;

import com.project.ticketBooking.dtos.UserDTO;
import com.project.ticketBooking.dtos.UserLoginDTO;
import com.project.ticketBooking.exceptions.DataNotFoundException;
import com.project.ticketBooking.models.User;
import com.project.ticketBooking.responses.LoginResponse;
import com.project.ticketBooking.responses.UserResponse;
import com.project.ticketBooking.services.UserService;
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

class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_withValidData_returnsNewUser() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setPassword("password");
        userDTO.setRetypePassword("password");
        User newUser = new User();
        when(userService.createUser(userDTO)).thenReturn(newUser);
        when(bindingResult.hasErrors()).thenReturn(false);

        ResponseEntity<?> response = userController.createUser(userDTO, bindingResult);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(newUser, response.getBody());
    }

    @Test
    void createUser_withInvalidData_returnsErrorMessages() {
        UserDTO userDTO = new UserDTO();
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(new FieldError("userDTO", "email", "Email is required")));

        ResponseEntity<?> response = userController.createUser(userDTO, bindingResult);

        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof List);
        assertEquals("Email is required", ((List<?>) response.getBody()).get(0));
    }

    @Test
    void createUser_withMismatchedPasswords_returnsErrorMessage() {
        UserDTO userDTO = new UserDTO();
        userDTO.setPassword("password");
        userDTO.setRetypePassword("differentPassword");
        when(bindingResult.hasErrors()).thenReturn(false);

        ResponseEntity<?> response = userController.createUser(userDTO, bindingResult);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Password does not match", response.getBody());
    }

    @Test
    void login_withValidCredentials_returnsToken() throws Exception {
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setEmail("test@example.com");
        userLoginDTO.setPassword("password");
        String token = "validToken";
        when(userService.login(userLoginDTO.getEmail(), userLoginDTO.getPassword(), 1L)).thenReturn(token);

        ResponseEntity<?> response = userController.login(userLoginDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Đăng nhập thành công", ((LoginResponse) response.getBody()).getMessage());
        assertEquals(token, ((LoginResponse) response.getBody()).getToken());
    }

    @Test
    void login_withInvalidCredentials_returnsErrorMessage() throws Exception {
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setEmail("test@example.com");
        userLoginDTO.setPassword("wrongPassword");
        when(userService.login(userLoginDTO.getEmail(), userLoginDTO.getPassword(), 1L)).thenThrow(new DataNotFoundException("Invalid credentials"));

        ResponseEntity<?> response = userController.login(userLoginDTO);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Invalid credentials", response.getBody());
    }

    @Test
    void getUserDetails_withValidToken_returnsUserDetails() throws Exception {
        String token = "Bearer validToken";
        User user = new User();
        when(userService.getUserDetailsFromToken("validToken")).thenReturn(user);

        ResponseEntity<?> response = userController.getUserDetails(token);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(UserResponse.fromUser(user), response.getBody());
    }

    @Test
    void getUserDetails_withInvalidToken_returnsErrorMessage() throws Exception {
        String token = "Bearer invalidToken";
        when(userService.getUserDetailsFromToken("invalidToken")).thenThrow(new RuntimeException("Invalid token"));

        ResponseEntity<?> response = userController.getUserDetails(token);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Invalid token", response.getBody());
    }
}