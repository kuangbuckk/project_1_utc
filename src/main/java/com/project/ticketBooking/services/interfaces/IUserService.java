package com.project.ticketBooking.services.interfaces;

import com.project.ticketBooking.dtos.EventImageDTO;
import com.project.ticketBooking.dtos.UserAvatarDTO;
import com.project.ticketBooking.dtos.UserDTO;
import com.project.ticketBooking.models.EventImage;
import com.project.ticketBooking.models.User;
import com.project.ticketBooking.models.UserAvatar;

public interface IUserService {
    User createUser(UserDTO userDTO) throws Exception; //register
    User getUserDetailsFromToken(String token) throws Exception;
    String login(String phoneNumber, String password, Long roleId) throws Exception;

    UserAvatar createuUserAvatar(Long eventId, UserAvatarDTO userAvatarDTO) throws Exception;
}