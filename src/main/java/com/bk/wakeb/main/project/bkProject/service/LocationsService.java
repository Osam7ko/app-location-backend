package com.bk.wakeb.main.project.bkProject.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bk.wakeb.main.project.bkProject.dto.LocationsDTO;
import com.bk.wakeb.main.project.bkProject.model.Locations;
import com.bk.wakeb.main.project.bkProject.model.User;
import com.bk.wakeb.main.project.bkProject.repository.LocationsRepo;
import com.bk.wakeb.main.project.bkProject.repository.UserRepository;

@Service
public class LocationsService {
	private final LocationsRepo locationsRepo;
    private final UserRepository userRepository;
    
    
	public LocationsService(LocationsRepo locationsRepo, UserRepository userRepository) {
		super();
		this.locationsRepo = locationsRepo;
		this.userRepository = userRepository;
	}
	
	private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new SecurityException("User not authenticated");
        }
        return authentication.getName();
    }

    private User getCurrentUser() {
        String username = getCurrentUsername();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    // Convert Entity <-> DTO
    private LocationsDTO toDTO(Locations Locations) {
        LocationsDTO dto = new LocationsDTO();
        dto.setId(Locations.getId());
        dto.setName(Locations.getName());
        dto.setLatitude(Locations.getLatitude());
        dto.setLongitude(Locations.getLongitude());
        dto.setUserId(Locations.getUser().getId());
        return dto;
    }

    private Locations toEntity(LocationsDTO dto) {
        Locations fav = new Locations();
        fav.setName(dto.getName());
        fav.setLatitude(dto.getLatitude());
        fav.setLongitude(dto.getLongitude());
        return fav;
    }

    public List<LocationsDTO> getAllLocationss(String username) {
    	// 1. Get the authenticated user
    	User user = userRepository.findByUsername(username)
    			.orElseThrow(() -> new UsernameNotFoundException("User not found"));
    	// 2. Get ALL tasks from repository
        List<Locations> allLocations = locationsRepo.findAll();
     // 3. Filter to only the user's tasks
        return allLocations
                .stream().filter(locations -> locations.getUser().getId().equals(user.getId()))
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    public LocationsDTO getLocationById(long id, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
        Locations location = locationsRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        
        if (!location.getUser().getId().equals(user.getId())) {
            throw new SecurityException("You can only view your own tasks");
        }
        
        return toDTO(location);
    }
    

    public LocationsDTO saveLocations(LocationsDTO dto) {
        User user = getCurrentUser();
        Locations fav = toEntity(dto);
        fav.setUser(user);
        return toDTO(locationsRepo.save(fav));
    }

    public void deleteLocations(Long id) {
        User user = getCurrentUser();
        Locations fav = locationsRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Locations not found"));
        if (!fav.getUser().getId().equals(user.getId())) {
            throw new SecurityException("Unauthorized");
        }
        locationsRepo.delete(fav);
    }
    
    
}
