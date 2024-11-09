package com.project.ticketBooking.repositories;

import com.project.ticketBooking.models.Category;
import com.project.ticketBooking.models.Event;
import com.project.ticketBooking.models.Organization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    Page<Event> findAll(Pageable pageable);
    List<Event> findByOrganizationId(Long organizationId);
    Page<Event> findByCategoryId(Long categoryId, Pageable pageable);

    @Query("SELECT e FROM Event e WHERE e.name LIKE %:keyword% OR e.description LIKE %:keyword%")
    Page<Event> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
