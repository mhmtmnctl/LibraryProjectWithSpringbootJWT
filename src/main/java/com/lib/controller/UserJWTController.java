package com.lib.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lib.security.JwtUtils;
import com.lib.service.UserService;
import com.lib.controller.dto.RegisterRequest;
import com.lib.controller.dto.UpdateRequestDTO;
import com.lib.domain.User;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping
@AllArgsConstructor
public class UserJWTController {
	@Autowired
	private UserService userService;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtUtils jwtUtils;
	
	@PostMapping("/register")
	public ResponseEntity<Map<String,String>> registerUser (@Valid @RequestBody RegisterRequest request ) {
		userService.registerUser(request);		
		Map<String,String> map = new HashMap<>();
		map.put("message",	" User registered successfuly");
		map.put("status", "true");
		return new ResponseEntity<>(map,HttpStatus.CREATED);		
	}
	
	@GetMapping("/login")
	public ResponseEntity<Map<String,String>> login(@Valid @RequestBody com.lib.controller.dto.LoginRequest request){
		
		Authentication  authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getUserMail(), request.getPassword()));
		
		String token = jwtUtils.generateToken(authentication);
		
		Map<String,String> map = new HashMap<>();
		map.put("token",	token);
		map.put("status", "true");
		return new ResponseEntity<>(map,HttpStatus.ACCEPTED);		
	}
	
	@GetMapping("/listUsers")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<User>> getAll() {		
		List<User> users=  userService.getAll();
		return ResponseEntity.ok(users);		
	}
	
	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")//sadece admin silebilsin.
    public ResponseEntity<Map<String,String>> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        Map<String,String> map = new HashMap<>();
        map.put("message", "User is deleted successfuly");
        map.put("status", "true");
        return new ResponseEntity<>(map,HttpStatus.OK);
        //todo kullanıcının iade etmediği kitabı varsa silinemesin.      
    }
	
	@PutMapping("/update/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')or hasRole('ROLE_USER')")
	public ResponseEntity<Map<String, String>> updateUser( @PathVariable Long id, @RequestBody UpdateRequestDTO updateRequestDTO){
		
		userService.updateUser(id,updateRequestDTO);
		Map<String,String> map = new HashMap<>();
		map.put("message", "User is updated successfuly");
		map.put("status", "true");
		return new ResponseEntity<>(map,HttpStatus.OK);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}