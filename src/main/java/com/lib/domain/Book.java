package com.lib.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="tbl_book")
public class Book {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column (name="book_id",length=100,nullable=false)
	private Long id;
	
	@NotNull(message = "Book name can not be null")
    @NotBlank(message = "Book name can not be white space")
    @Size(min = 1,max = 50,message = "Book name '${validatedValue}' must be between {min} and {max} long")
	@Column (name="bookName",length=100,nullable=false)
	private String bookName;
	
	@NotNull(message = "Author name can not be null")
    @NotBlank(message = "Author name can not be white space")
    @Size(min = 2,max = 50,message = "Author name '${validatedValue}' must be between {min} and {max} long")
	@Column (name="authorName",length=100,nullable=false)
	private String authorName;
	
	@NotNull(message = "Category name can not be null")
    @NotBlank(message = "Category name can not be white space")
    @Size(min = 2,max = 50,message = "Category name '${validatedValue}' must be between {min} and {max} long")
	@Column (name="category",length=100,nullable=false)
	private String category;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

}
