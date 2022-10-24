package com.lib.service;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lib.controller.dto.AddBookRequestDTO;
import com.lib.domain.Book;
import com.lib.repository.BookRepository;
import com.lib.repository.UserRepository;

@Service
public class BookService {
	
	@Autowired
	UserRepository userRepository;//gerek olmayabalir.
	
	@Autowired
	BookRepository bookRepository;
	
	public void addBook(@Valid AddBookRequestDTO request) {
		
		Book book = new Book();
		
		book.setBookName(request.getBookName());
		book.setAuthorName(request.getAuthorName());
		book.setCategory(request.getCategory());
		book.setStatus(request.getStatus());	
		book.setDate(request.getDate());
		book.setOwner(request.getOwner());
		
		bookRepository.save(book);			
	}

}
