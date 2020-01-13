package com.demo.Service;

import com.demo.Model.User;

public interface IUserService {
	
	public User save(User user);
	
	User mergeAsNew(User user);

	public User findById(Long id);

	public User findByEmail(String email);

}
