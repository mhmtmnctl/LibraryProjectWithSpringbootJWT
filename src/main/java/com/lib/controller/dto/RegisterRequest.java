package com.lib.controller.dto;

import java.util.Set;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.lib.controller.dto.RegisterRequest;
import lombok.Data;

@Data
public class RegisterRequest {

	@NotNull(message = "First name can not be null")
    @NotBlank(message = "last name can not be white space")
    @Size(min = 2,max = 25,message = "First name '${validatedValue}' must be between {min} and {max} long")	
	private String firstName;

	@NotNull(message = "Last name can not be null")
    @NotBlank(message = "last name can not be white space")
    @Size(min = 2,max = 25,message = "Last name '${validatedValue}' must be between {min} and {max} long")	
	private String lastName;

	
	@NotNull(message = "Email can not be null")
    @NotBlank(message = "Email can not be white space")
    @Size(min = 5,max = 50,message = "Email '${validatedValue}' must be between {min} and {max} long")
	@Email(message="Provide valid email")	
	private String userMail;
	
	
	@NotNull(message = "Password can not be null")
    @NotBlank(message = "Password can not be white space")
    @Size(min =4,max = 255,message = "Password '${validatedValue}' must be between {min} and {max} long")	
	private String password;

	@NotNull(message = "PhoneNumber can not be null")
    @NotBlank(message = "PhoneNumber can not be white space")
    @Size(min = 10,max = 20,message = "PhoneNumber '${validatedValue}' must be between {min} and {max} long")	
	private String phoneNumber;	
	
	private Set<String> roles;
}
