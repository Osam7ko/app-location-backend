package com.bk.map.main.project.bkProject.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bk.map.main.project.bkProject.dto.UserDTO;
import com.bk.map.main.project.bkProject.model.User;
import com.bk.map.main.project.bkProject.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}
	
	public UserDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        return dto;
    }
	
//	public User regster(User user) {
//		if (userRepository.existsByUsername(user.getUsername())) {
//            throw new RuntimeException("User already exists");
//        }
//        return userRepository.save(user);
//	}
}
