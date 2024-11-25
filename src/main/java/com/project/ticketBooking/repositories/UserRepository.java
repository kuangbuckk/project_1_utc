package com.project.ticketBooking.repositories;

import com.project.ticketBooking.models.User;
import io.micrometer.common.lang.NonNullApi;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@NonNullApi
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    Optional<User> getUserById(Long userId);
    Optional<User> findByEmail(String email);
}
