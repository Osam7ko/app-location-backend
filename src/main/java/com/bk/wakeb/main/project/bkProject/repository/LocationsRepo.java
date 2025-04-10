package com.bk.wakeb.main.project.bkProject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bk.wakeb.main.project.bkProject.model.Locations;

public interface LocationsRepo extends JpaRepository<Locations, Long> {
    List<Locations> findByUserUsername(String username);
}
