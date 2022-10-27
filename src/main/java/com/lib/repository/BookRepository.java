package com.lib.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.lib.domain.Book;


@Repository 
public interface BookRepository extends JpaRepository<Book, Long>{

	List<Book> findAllByOwner(String mail);

	@Query(value="SELECT * FROM tbl_book b WHERE b.status=:pstatus", nativeQuery=true)
    List<Book> getAvailableBooks(@Param ("pstatus")Boolean isAvailable );
	
}
