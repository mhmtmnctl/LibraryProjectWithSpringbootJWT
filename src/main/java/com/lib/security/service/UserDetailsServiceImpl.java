package com.lib.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.lib.domain.User;
import com.lib.exception.ResourceNotFoundException;
import com.lib.repository.UserRepository;
@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	private UserRepository userRepository;	
	@Autowired
	public UserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String userMail) throws UsernameNotFoundException {
		
		User user = userRepository.findByUserMail(userMail).
				orElseThrow(()-> new ResourceNotFoundException("User not found with username : "+ userMail));
		return UserDetailsImpl.build(user);
	}
}