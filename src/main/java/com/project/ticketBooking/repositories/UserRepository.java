package com.project.ticketBooking.repositories;

import com.project.ticketBooking.models.User;

import java.util.Optional;

public interface UserRepository {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
}
