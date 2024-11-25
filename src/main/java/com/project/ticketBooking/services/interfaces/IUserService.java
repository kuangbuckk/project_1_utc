package com.project.ticketBooking.services.interfaces;

import com.project.ticketBooking.dtos.*;
import com.project.ticketBooking.models.EventImage;
import com.project.ticketBooking.models.User;
import com.project.ticketBooking.models.UserAvatar;
import com.project.ticketBooking.responses.UserResponse;

import java.util.List;

public interface IUserService {
    List<UserResponse> getAllUsers();
    User getUserById(Long id) throws Exception;
    User createUser(UserDTO userDTO) throws Exception; //register
    User getUserDetailsFromToken(String token) throws Exception;
    String login(String phoneNumber, String password, Long roleId) throws Exception;
    User updateUser(String token, UserUpdateDTO userUpdateDTO) throws Exception;
    UserAvatar createuUserAvatar(Long userId, UserAvatarDTO userAvatarDTO) throws Exception;
    User updateUserAdminAction(Long userId,  UserUpdateAdminDTO userUpdateAdminDTO) throws Exception;
}