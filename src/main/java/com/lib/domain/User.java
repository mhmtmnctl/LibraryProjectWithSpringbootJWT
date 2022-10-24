package com.lib.domain;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
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
@Table(name = "tbl_user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_id")
	private Long id;

	@NotNull(message = "First name can not be null")
    @NotBlank(message = "last name can not be white space")
    @Size(min = 2,max = 25,message = "First name '${validatedValue}' must be between {min} and {max} long")
	@Column(name="firstName",length = 25, nullable = false)
	private String firstName;

	@NotNull(message = "Last name can not be null")
    @NotBlank(message = "last name can not be white space")
    @Size(min = 2,max = 25,message = "Last name '${validatedValue}' must be between {min} and {max} long")
	@Column(name="lastName",length = 25, nullable = false)
	private String lastName;

	
	@NotNull(message = "Email can not be null")
    @NotBlank(message = "Email can not be white space")
    @Size(min = 5,max = 50,message = "Email '${validatedValue}' must be between {min} and {max} long")
	@Email(message="Provide valid email")
	@Column(name="userMail",length = 50, nullable = false, unique = true)
	private String userMail;
	
	
	@NotNull(message = "Password can not be null")
    @NotBlank(message = "Password can not be white space")
    @Size(min =4,max = 255,message = "Password '${validatedValue}' must be between {min} and {max} long")
	@Column(name="password",length = 255, nullable = false)
	private String password;

	@NotNull(message = "PhoneNumber can not be null")
    @NotBlank(message = "PhoneNumber can not be white space")
    @Size(min = 10,max = 20,message = "PhoneNumber '${validatedValue}' must be between {min} and {max} long")
	@Column(name="phoneNumber",length = 20, nullable = true)
	private String phoneNumber;	
	
	//@OneToMany(mappedBy="user",orphanRemoval=true)
	//private List<Book> books=new ArrayList<>();
	
	@JoinTable(name="tbl_user_role",joinColumns=@JoinColumn(name="user_id"),inverseJoinColumns = @JoinColumn(name="role_id"))
	@ManyToMany(fetch=FetchType.EAGER)
	private Set<Role> roles=new HashSet<>();		
}