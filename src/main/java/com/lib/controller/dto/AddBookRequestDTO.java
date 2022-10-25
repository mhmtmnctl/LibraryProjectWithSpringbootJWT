package com.lib.controller.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
@Data
public class AddBookRequestDTO {

	@NotNull(message = "Book name can not be null")
    @NotBlank(message = "Book name can not be white space")
    @Size(min = 1,max = 50,message = "Book name '${validatedValue}' must be between {min} and {max} long")

	private String bookName;
	
	@NotNull(message = "Author name can not be null")
    @NotBlank(message = "Author name can not be white space")
    @Size(min = 2,max = 50,message = "Author name '${validatedValue}' must be between {min} and {max} long")

	private String authorName;
	
	@NotNull(message = "Category name can not be null")
    @NotBlank(message = "Category name can not be white space")
    @Size(min = 2,max = 50,message = "Category name '${validatedValue}' must be between {min} and {max} long")

	private String category;
	//alinaBilirMi,alinmaTarihi,alanKisi
	
		//kitap durumu 
	
	private Boolean status;
	
	//alınma tarihi
	
	private String date;
	
	//alan kişi maili
	
	private String owner;
	
}
