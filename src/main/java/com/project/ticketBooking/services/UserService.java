package com.project.ticketBooking.services;

import com.project.ticketBooking.component.JwtTokenUtils;
import com.project.ticketBooking.dtos.UserAvatarDTO;
import com.project.ticketBooking.dtos.UserDTO;
import com.project.ticketBooking.exceptions.DataNotFoundException;
import com.project.ticketBooking.exceptions.PermissionDenyException;
import com.project.ticketBooking.models.Role;
import com.project.ticketBooking.models.User;
import com.project.ticketBooking.models.UserAvatar;
import com.project.ticketBooking.repositories.RoleRepository;
import com.project.ticketBooking.repositories.UserRepository;
import com.project.ticketBooking.services.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public User createUser(UserDTO userDTO) throws Exception {
        String email = userDTO.getEmail();
        if(userRepository.existsByEmail(email)) {
            throw new DataIntegrityViolationException("Email already exists");
        }
        Role role = roleRepository.findById(userDTO.getRoleId())
                .orElseThrow(() -> new DataNotFoundException("Role not found"));
        if(role.getName().toUpperCase().equals(Role.ADMIN)) {
            throw new PermissionDenyException("You can not register a admin account!");
        }
        //Convert userDTO to User
        User newUser = User.builder()
                .fullName(userDTO.getFullName())
                .phoneNumber(userDTO.getPhoneNumber())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .address(userDTO.getAddress())
                .dateOfBirth(userDTO.getDateOfBirth())
                .facebookAccountId(userDTO.getFacebookAccountId())
                .googleAccountId(userDTO.getGoogleAccountId())
                .isActive(1)
                .build();
        newUser.setRole(role);
        if(userDTO.getFacebookAccountId() == 0 && userDTO.getGoogleAccountId() == 0) {
            String password = userDTO.getPassword();
            String encodedPassword = passwordEncoder.encode(password);
            newUser.setPassword(encodedPassword);
        }
        else {
            String randomPassword = "randomPASSWORD";
            String encodedPassword = passwordEncoder.encode(randomPassword);
            newUser.setPassword(encodedPassword);
        }
        return userRepository.save(newUser);
    }

    @Override
    public User getUserDetailsFromToken(String token) throws Exception {
//        if (!jwtTokenUtil.va(token)) {
//            throw new DataNotFoundException("Token is expired");
//        }
        String email = jwtTokenUtil.extractEmail(token);
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new DataNotFoundException("User not found");
        }
    }

    @Override
    public String login(String email, String password, Long roleId) throws Exception {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty()) {
            throw new DataNotFoundException("Email/password is not correct");
        }
        User existingUser = optionalUser.get();
        //check password
        if(existingUser.getFacebookAccountId() == 0
                && existingUser.getGoogleAccountId() == 0) {
            if(!passwordEncoder.matches(password, existingUser.getPassword())){
                throw new BadCredentialsException("Email/password is not correct");
            }
        }
        Optional<Role> optionalRole = roleRepository.findById(roleId);
        if(optionalRole.isEmpty() || !roleId.equals(existingUser.getRole().getId())) {
            throw new DataNotFoundException("Role does not exists");
        }
        if(optionalUser.get().isActive() != 1) {
            throw new DataNotFoundException("User is not active");
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                email, password, existingUser.getAuthorities()
        );
        //authenticate with Java Spring Security
        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtil.generateToken(existingUser); //want to return JWT token ?
    }

    @Override
    public UserAvatar createuUserAvatar(Long eventId, UserAvatarDTO userAvatarDTO) throws Exception {
        return null;
    }
}
