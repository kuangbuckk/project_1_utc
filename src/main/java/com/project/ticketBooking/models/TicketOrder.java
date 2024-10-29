package com.project.ticketBooking.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "ticket_orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketOrder extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Min(value = 1, message = "User ID must be greater than 0")
    private User user;

    @Column(name = "order_date", columnDefinition = "date default current_timestamp")
    private LocalDate orderDate;

    @Column(name = "total_money", columnDefinition = "float default null")
    @Min(value = 0, message = "Total money must be greater than or equal to 0")
    private Float totalMoney;

    @Column(name = "payment_method", columnDefinition = "varchar(100) default null")
    private String paymentMethod;

    @Column(name = "payment_status", columnDefinition = "varchar(100) default null")
    private String paymentStatus;
}