package com.bk.map.main.project.bkProject.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.bk.map.main.project.bkProject.dto.LocationsDTO;
import com.bk.map.main.project.bkProject.model.Locations;
import com.bk.map.main.project.bkProject.model.User;
import com.bk.map.main.project.bkProject.repository.LocationsRepo;
import com.bk.map.main.project.bkProject.repository.UserRepository;
import com.bk.map.main.project.bkProject.service.LocationsService;

public class LocationsServiceTest {

    private LocationsRepo locationsRepo;
    private UserRepository userRepository;
    private LocationsService locationsService;

    @BeforeEach
    void setUp() {
        locationsRepo = mock(LocationsRepo.class);
        userRepository = mock(UserRepository.class);
        locationsService = new LocationsService(locationsRepo, userRepository);

        // Setup mock authenticated user
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("testuser");
        when(auth.isAuthenticated()).thenReturn(true);

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void testSaveLocation() {
        // Given
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername("testuser");

        LocationsDTO dto = new LocationsDTO();
        dto.setName("Test Location");
        dto.setLatitude(24.7);
        dto.setLongitude(46.7);

        Locations savedLocation = new Locations();
        savedLocation.setId(100L);
        savedLocation.setName(dto.getName());
        savedLocation.setLatitude(dto.getLatitude());
        savedLocation.setLongitude(dto.getLongitude());
        savedLocation.setUser(mockUser);

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(mockUser));
        when(locationsRepo.save(Mockito.any(Locations.class))).thenReturn(savedLocation);

        // When
        LocationsDTO result = locationsService.saveLocations(dto);

        // Then
        assertNotNull(result);
        assertEquals(dto.getName(), result.getName());
        assertEquals(dto.getLatitude(), result.getLatitude());
        assertEquals(dto.getLongitude(), result.getLongitude());
        assertEquals(mockUser.getId(), result.getUserId());
    }
    
    @Test
    void testGetAllLocationss() {
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername("testuser");

        Locations location1 = new Locations();
        location1.setId(1L);
        location1.setName("Loc 1");
        location1.setLatitude(24.7);
        location1.setLongitude(46.7);
        location1.setUser(mockUser);

        Locations location2 = new Locations();
        location2.setId(2L);
        location2.setName("Loc 2");
        location2.setLatitude(25.0);
        location2.setLongitude(47.0);
        location2.setUser(mockUser);

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(mockUser));
        when(locationsRepo.findAll()).thenReturn(List.of(location1, location2));

        List<LocationsDTO> results = locationsService.getAllLocationss("testuser");

        assertEquals(2, results.size());
        assertEquals("Loc 1", results.get(0).getName());
        assertEquals("Loc 2", results.get(1).getName());
    }
    
}