package com.bk.wakeb.main.project.bkProject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bk.wakeb.main.project.bkProject.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByUsername(String user);
	Optional<User> findByUsername(User user);
	void deleteByUsername(String username);
	boolean existsByUsername(String username);
}
