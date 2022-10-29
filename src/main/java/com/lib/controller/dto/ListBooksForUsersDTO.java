package com.lib.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ListBooksForUsersDTO {
	
	
	private String bookName;
		
	private String authorName;
		
	private String category;

	private Boolean status;

}
