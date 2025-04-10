package com.bk.wakeb.main.project.bkProject.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bk.wakeb.main.project.bkProject.dto.LocationsDTO;
import com.bk.wakeb.main.project.bkProject.service.LocationsService;

@RestController
@RequestMapping("/api/location")
public class LocationsController {
	private final LocationsService locationsService;

	public LocationsController(LocationsService locationsService) {
		super();
		this.locationsService = locationsService;
	}
	
	@GetMapping
    public List<LocationsDTO> getLocationss(Authentication authentication) {
		String username = authentication.getName();
        return locationsService.getAllLocationss(username);
    }
	
	@GetMapping("/{id}")
	public LocationsDTO getLocationById(@PathVariable Long id,Authentication authentication) {
		String username = authentication.getName();
	    return locationsService.getLocationById(id,username);
	}

    @PostMapping
    public LocationsDTO addLocations(@RequestBody LocationsDTO LocationsDTO, Authentication authentication) {
    	String username = authentication.getName();
        return locationsService.saveLocations(LocationsDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteLocations(@PathVariable Long id, Authentication authentication) {
    	String username = authentication.getName();
    	locationsService.deleteLocations(id);
    }
}
