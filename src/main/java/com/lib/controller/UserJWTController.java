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
import com.lib.controller.dto.AddBookRequestDTO;
import com.lib.controller.dto.LoginRequest;
import com.lib.controller.dto.RegisterRequest;
import com.lib.controller.dto.UpdateRequestDTO;
import com.lib.domain.Book;
import com.lib.domain.User;
import com.lib.security.JwtUtils;
import com.lib.service.BookService;
import com.lib.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping
@AllArgsConstructor
@NoArgsConstructor


public class UserJWTController {
	@Autowired//@allargscons olunca autowired gerek kalmıyor
	private UserService userService;
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtUtils jwtUtils;

	
	@Autowired
	public static String loginOlanUserMaili;
	
	@PostMapping("/register")
	public ResponseEntity<Map<String,String>> registerUser (@Valid @RequestBody RegisterRequest request ) {
		userService.registerUser(request);		
		Map<String,String> map = new HashMap<>();
		map.put("message",	" User registered successfuly");
		map.put("status", "true");
		return new ResponseEntity<>(map,HttpStatus.CREATED);		
	}
	
	@GetMapping("/login")
	public ResponseEntity<Map<String,String>> login(@Valid @RequestBody LoginRequest request){
		
		Authentication  authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getUserMail(), request.getPassword()));
		
		  loginOlanUserMaili=request.getUserMail();
		
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
	
	@DeleteMapping("/deleteUser/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")//sadece admin silebilsin.
    public ResponseEntity<Map<String,String>> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        Map<String,String> map = new HashMap<>();
        map.put("message", "User is deleted successfuly");
        map.put("status", "true");
        return new ResponseEntity<>(map,HttpStatus.OK);
        //todo kullanıcının iade etmediği kitabı varsa silinemesin.      
    }
	
	@PutMapping("/updateUser/{id}")//.../students/1
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<Map<String, String>> updateUser( @PathVariable Long id, @RequestBody UpdateRequestDTO updateRequestDTO){

        userService.updateUser(id,updateRequestDTO);
        Map<String,String> map = new HashMap<>();
        map.put("message", "User is updated successfuly");
        map.put("status", "true");
        return new ResponseEntity<>(map,HttpStatus.OK);
    }
	
	//kitap ekleme
	@PostMapping("/addBook")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Map<String,String>> addBook (@Valid @RequestBody AddBookRequestDTO request ) {
		bookService.addBook(request);		
		Map<String,String> map = new HashMap<>();
		map.put("message",	" Book added successfuly");
		map.put("status", "true");
		return new ResponseEntity<>(map,HttpStatus.CREATED);		
	}
	
	//kitap listeleme
	
	@GetMapping("/listBooks")
	@PreAuthorize("hasRole('ROLE_ADMIN')or hasRole('ROLE_USER')")
	public ResponseEntity<List<Book>> getAllBooks() {		
		List<Book> books=  bookService.getAllBooks();
		return ResponseEntity.ok(books);		
	}
	
	//kitap silme
	
	@DeleteMapping("/deleteBook/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")//sadece admin silebilsin.
    public ResponseEntity<Map<String,String>> deleteBook(@PathVariable("id") Long id) {
        bookService.deleteBook(id);
        Map<String,String> map = new HashMap<>();
        map.put("message", "Book is deleted successfuly");
        map.put("status", "true");
        return new ResponseEntity<>(map,HttpStatus.OK);		
	}
	
	//kitap güncelleme
	
	@PutMapping("/updateBook/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')or hasRole('ROLE_USER')")
	public ResponseEntity<Map<String, String>> updateBook( @PathVariable Long id, @RequestBody AddBookRequestDTO addBookRequestDTO){
		
		bookService.updateBook(id,addBookRequestDTO);
		Map<String,String> map = new HashMap<>();
		map.put("message", "Book is updated successfuly");
		map.put("status", "true");
		return new ResponseEntity<>(map,HttpStatus.OK);
	}
	
	//kullanıcı kitap alcak...
	
	@PutMapping("/getBook/{id}")//kitap bilgilerini güncellediğimiz için put olsun.
	@PreAuthorize("hasRole('ROLE_USER')")
	 public ResponseEntity<Map<String,String>> getBook(@PathVariable("id") Long id,String loginOlanUserMaili ) {
	
        bookService.getBook(id,UserJWTController.loginOlanUserMaili);
        Map<String,String> map = new HashMap<>();
        map.put("message", "Book is taken successfuly");
        map.put("status", "true");
        map.put("taken by", UserJWTController.loginOlanUserMaili);
        return new ResponseEntity<>(map,HttpStatus.OK);		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}