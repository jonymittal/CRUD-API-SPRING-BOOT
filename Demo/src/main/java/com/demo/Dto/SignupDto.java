package com.demo.Dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SignupDto {
	
	@Email(message = "Please enter valid email addresss.")
	private String email;

	@NotNull(message = "Please enter a password.")
	@Size(min = 8, message = "Password must be atleast 8 character long")
	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
