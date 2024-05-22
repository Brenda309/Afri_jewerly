package com.afri.jewerly.afri_jewerly.controller;


import com.afri.jewerly.afri_jewerly.DTO.LoginDto;
import com.afri.jewerly.afri_jewerly.DTO.RegisterDto;
import com.afri.jewerly.afri_jewerly.entity.Role;
import com.afri.jewerly.afri_jewerly.entity.Users;
import com.afri.jewerly.afri_jewerly.repository.RoleRepository;
import com.afri.jewerly.afri_jewerly.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
   @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(),loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("User signed-in successfully!",HttpStatus.OK);
    }
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody RegisterDto registerDto) {
        // Check whether the username exists
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            return new ResponseEntity<>("Username already exists!", HttpStatus.BAD_REQUEST);
        }

        // Check whether the email exists
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            return new ResponseEntity<>("Email already taken", HttpStatus.BAD_REQUEST);
        }

        // Create user object
        Users user = new Users();
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        // Assign roles
        Set<Role> roles = new HashSet<>();
        if ("admin@gmail.com".equals(registerDto.getEmail())) {
            // Assign ADMIN role if the email is admin@gmail.com
            Role adminRole = roleRepository.findByName("ROLE_ADMIN").orElseThrow(() -> new RuntimeException("Admin role not found"));
            roles.add(adminRole);
        } else {
            // Assign USER role to other users
            Role userRole = roleRepository.findByName("ROLE_USER").orElseThrow(() -> new RuntimeException("User role not found"));
            roles.add(userRole);
        }
        user.setRoles(roles);

        userRepository.save(user);
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }
    }