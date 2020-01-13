package com.demo.Dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class LoginDto {

	@Email(message = "Please enter valid email addresss.")
	@NotNull(message = "Please enter an email.")
	private String email;

	@NotNull(message = "Please enter a password.")
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
