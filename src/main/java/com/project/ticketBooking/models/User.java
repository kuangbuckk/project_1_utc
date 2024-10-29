package com.project.ticketBooking.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", length = 100, columnDefinition = "varchar(100) default ''")
    private String fullName;

    @Column(name = "phone_number", length = 15, columnDefinition = "varchar(15) default ''")
    private String phoneNumber;

    @NotNull(message = "Email cannot be null")
    @Email(message = "Email should be valid")
    @Column(name = "email", length = 120, nullable = false, unique = true)
    private String email;

    @Column(name = "address", length = 200, columnDefinition = "varchar(200) default ''")
    private String address;

    @NotNull(message = "Password cannot be null")
    @Column(name = "password", length = 200, nullable = false, columnDefinition = "varchar(200) default ''")
    private String password;

    @Column(name = "created_at", columnDefinition = "timestamp default current_timestamp")
    private LocalDate createdAt;

    @Column(name = "updated_at", columnDefinition = "timestamp default current_timestamp")
    private LocalDate updatedAt;

    @Column(name = "is_active", columnDefinition = "smallint default 1")
    private Short isActive;

    @Column(name = "date_of_birth", columnDefinition = "date")
    private LocalDate dateOfBirth;

    @Column(name = "facebook_account_id", columnDefinition = "int default 0")
    private Integer facebookAccountId;

    @Column(name = "google_account_id", columnDefinition = "int default 0")
    private Integer googleAccountId;

    @NotNull(message = "Role ID cannot be null")
    @Min(value = 1, message = "Role ID must be greater than 0")
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
}