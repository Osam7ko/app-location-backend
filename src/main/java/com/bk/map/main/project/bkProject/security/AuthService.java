package com.bk.map.main.project.bkProject.security;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bk.map.main.project.bkProject.model.User;
import com.bk.map.main.project.bkProject.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class AuthService {
	 private final UserRepository userRepository;
	    private final PasswordEncoder passwordEncoder;

	    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
	        this.userRepository = userRepository;
	        this.passwordEncoder = passwordEncoder;
	    }

	    public User register(User user) {
	        if (userRepository.existsByUsername(user.getUsername())) {
	            throw new RuntimeException("Username already exists");
	        }
	        user.setPassword(passwordEncoder.encode(user.getPassword()));
	        return userRepository.save(user);
	    }
	    
	    public boolean authenticate(String username, String password) {
	        Optional<User> optionalUser = userRepository.findByUsername(username);
	        if (optionalUser.isPresent()) {
	            User user = optionalUser.get();
	            return passwordEncoder.matches(password, user.getPassword());
	        }
	        return false;
	    }
	    
	    @Transactional
	    public boolean deleteByUsername(String username) {
	        Optional<User> user = userRepository.findByUsername(username);
	        if (user.isPresent()) {
	            userRepository.delete(user.get());
	            return true;
	        }
	        return false;
	    }
}
