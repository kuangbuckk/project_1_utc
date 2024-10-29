package com.project.ticketBooking.repositories;

import com.project.ticketBooking.models.UserAvatar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAvatarRepository extends JpaRepository<UserAvatar, Long> {
    UserAvatar findByUserId(Long userId);
}
