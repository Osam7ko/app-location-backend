package com.bk.map.main.project.bkProject.controller;

import java.util.Collections;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bk.map.main.project.bkProject.dto.UserDTO;
import com.bk.map.main.project.bkProject.model.User;
import com.bk.map.main.project.bkProject.repository.UserRepository;
import com.bk.map.main.project.bkProject.security.AuthService;
import com.bk.map.main.project.bkProject.security.JwtUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
   
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    
    

    public AuthController(AuthService authService, 
            AuthenticationManager authenticationManager,
            JwtUtil jwtUtil ,UserRepository userRepository) {
				this.authService = authService;
				this.authenticationManager = authenticationManager;
				this.jwtUtil = jwtUtil;
				this.userRepository = userRepository;
	}

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            User registeredUser = authService.register(user);
            return ResponseEntity.ok(registeredUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Registration failed: " + e.getMessage());
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            
            if (authentication.isAuthenticated()) {
                final String jwt = jwtUtil.generateToken(authentication);
                return ResponseEntity.ok(Collections.singletonMap("token", jwt));
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }
    
    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable String username) {
        boolean deleted = authService.deleteByUsername(username);
        if (deleted) {
            return ResponseEntity.ok("User deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        
        return ResponseEntity.ok(userDTO);
    }
    
}