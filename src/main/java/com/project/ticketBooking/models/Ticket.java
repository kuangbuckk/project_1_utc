package com.project.ticketBooking.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

@Entity
@Table(name = "tickets")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ticket extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status", nullable = false, columnDefinition = "varchar(20) default 'active'")
    private String status;

    @ManyToOne
    @JoinColumn(name = "ticket_category_id", nullable = false)
    @Min(value = 1, message = "Ticket category ID must be greater than 0")
    private TicketCategory ticketCategory;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @Min(value = 1, message = "User ID must be greater than 0")
    private User user;
}