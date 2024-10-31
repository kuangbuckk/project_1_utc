package com.project.ticketBooking.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends BaseEntity implements UserDetails{
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

    @Column(name = "is_active", columnDefinition = "int default 1")
    private Integer isActive;

    @Column(name = "date_of_birth", columnDefinition = "date")
    private LocalDate dateOfBirth;

    @Column(name = "facebook_account_id", columnDefinition = "int default 0")
    private Integer facebookAccountId;

    @Column(name = "google_account_id", columnDefinition = "int default 0")
    private Integer googleAccountId;

    @NotNull(message = "Role ID cannot be null")
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE_"+getRole().getName()));
        return authorityList;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public int isActive() {
        return this.isActive;
    }
}