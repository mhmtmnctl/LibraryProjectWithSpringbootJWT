package com.lib.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.lib.repository.RoleRepository;
import com.lib.repository.UserRepository;
import com.lib.controller.dto.UpdateRequestDTO;
import com.lib.domain.Role;
import com.lib.domain.User;
import com.lib.domain.enums.UserRole;
import com.lib.exception.ConflictException;
import com.lib.exception.ResourceNotFoundException;

@Service
public class UserService {
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public void registerUser(com.lib.controller.dto.RegisterRequest request) {
		
		if(userRepository.existsByUserMail(request.getUserMail())) {
			throw new ConflictException("User is already registered ");
		}
		
		 Role role  = roleRepository.findByName(UserRole.ROLE_USER).orElseThrow(
				 ()-> new ResourceNotFoundException("Role Not Found "));
		Set<Role> roles = new HashSet<>();
		roles.add(role);
		User user = new User();
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		user.setUserMail(request.getUserMail());
		user.setPhoneNumber(request.getPhoneNumber());
		user.setRoles(roles);
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		
		userRepository.save(user);		
	}
	
	public  List<User> getAll() {

        return userRepository.findAll();
    }
	
	public void deleteUser(Long id) {
		 User user = findUser(id);
		 userRepository.delete(user);		
	}
	
	public User findUser(Long id) {
		return userRepository.findById(id).
		orElseThrow(()-> new ResourceNotFoundException("User not found with id : " + id));
	}
			
	public void updateUser(Long id, UpdateRequestDTO updateRequestDTO) {
		
	boolean emailExist=	userRepository.existsByUserMail(updateRequestDTO.getUserMail());
	
	User user= findUser(id);
	
	if (emailExist && !updateRequestDTO.getUserMail().equals(user.getUserMail())) {
		throw new ConflictException("email is already exist");
		
	}
	user.setFirstName(updateRequestDTO.getFirstName());
	user.setLastName(updateRequestDTO.getLastName());
	user.setUserMail(updateRequestDTO.getUserMail());
	user.setPhoneNumber(updateRequestDTO.getPhoneNumber());
	user.setPassword(passwordEncoder.encode(updateRequestDTO.getPassword()));
	userRepository.save(user);
	
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
