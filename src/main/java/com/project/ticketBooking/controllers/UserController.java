package com.project.ticketBooking.controllers;

import com.project.ticketBooking.dtos.UserDTO;
import com.project.ticketBooking.dtos.UserLoginDTO;
import com.project.ticketBooking.dtos.UserUpdateAdminDTO;
import com.project.ticketBooking.dtos.UserUpdateDTO;
import com.project.ticketBooking.exceptions.DataNotFoundException;
import com.project.ticketBooking.models.Ticket;
import com.project.ticketBooking.models.User;
import com.project.ticketBooking.responses.LoginResponse;
import com.project.ticketBooking.responses.UserResponse;
import com.project.ticketBooking.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/admin/retrieveAll")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> userResponses = userService.getAllUsers();
        return ResponseEntity.ok(userResponses);
    }

    @GetMapping("/admin/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            User user = userService.getUserById(id);
            return ResponseEntity.ok(UserResponse.fromUser(user));
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(
            @Valid @RequestBody UserDTO userDTO,
            BindingResult result
    ) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            if (!userDTO.getPassword().equals(userDTO.getRetypePassword())) {
                return ResponseEntity.badRequest().body("Password does not match");
            }
            User newUser = userService.createUser(userDTO);
            return ResponseEntity.ok(newUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid @RequestBody UserLoginDTO userLoginDTO) throws DataNotFoundException {
        try {
            // Kiểm tra thông tin đăng nhập và sinh token
            String token = userService.login(
                    userLoginDTO.getEmail(),
                    userLoginDTO.getPassword(),
                    userLoginDTO.getRoleId() == null ? 1 : userLoginDTO.getRoleId());
            return ResponseEntity.ok(LoginResponse.builder()
                    .message("Đăng nhập thành công")
                    .token(token)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/details")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_ORGANIZER')")
    public ResponseEntity<?> getUserDetails(@RequestHeader("Authorization") String token) {
        try {
            // Kiểm tra thông tin đăng nhập và sinh token
            String extractedToken = token.substring(7);
            User user = userService.getUserDetailsFromToken(extractedToken);
            return ResponseEntity.ok(UserResponse.fromUser(user));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

//    @PostMapping("/avatar/{userId}")
//    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
//    public ResponseEntity<?> updateUserAvatar(
//            @PathVariable Long userId,
//            @RequestParam String avatarUrl
//    ) {
//        try {
//            User updatedUser = userService.updateUserAvatar(userId, avatarUrl);
//            return ResponseEntity.ok(updatedUser);
//        } catch (DataNotFoundException e) {
//            return ResponseEntity.status(404).body(e.getMessage());
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body(e.getMessage());
//        }
//    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateUser(
            @RequestHeader("Authorization") String token,
            @Valid @RequestBody UserUpdateDTO userUpdateDTO,
            BindingResult result
    ) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            if (!userUpdateDTO.getNewPassword().equals(userUpdateDTO.getRetypeNewPassword())) {
                return ResponseEntity.badRequest().body("Password does not match");
            }
            String extractedToken = token.substring(7);
            User updatedUser = userService.updateUser(extractedToken, userUpdateDTO);
            UserResponse userResponse = UserResponse.fromUser(updatedUser);
            return ResponseEntity.ok(userResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/admin/updateUser/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateUserOrganization(
            @PathVariable("userId") Long userId,
            @RequestBody UserUpdateAdminDTO userUpdateAdminDTO
            ) {
        try {
            User updatedUser = userService.updateUserAdminAction(userId, userUpdateAdminDTO);
            return ResponseEntity.ok(UserResponse.fromUser(updatedUser));
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}
