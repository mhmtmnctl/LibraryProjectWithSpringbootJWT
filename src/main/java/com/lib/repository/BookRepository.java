package com.lib.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lib.domain.Book;

@Repository 
public interface BookRepository extends JpaRepository<Book, Long>{
	
	
}
