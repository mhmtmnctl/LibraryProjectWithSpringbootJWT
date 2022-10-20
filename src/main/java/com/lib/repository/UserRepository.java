package com.lib.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lib.domain.User;
import com.lib.exception.ResourceNotFoundException;

@Repository 
public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByUserMail(String userMail) throws ResourceNotFoundException;
	Boolean existsByUserMail(String userMail);

}
