package com.project.ticketBooking.repositories;

import com.project.ticketBooking.models.Category;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
