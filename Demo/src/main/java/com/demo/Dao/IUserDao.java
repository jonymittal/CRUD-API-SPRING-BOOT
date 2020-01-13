package com.demo.Dao;

import com.demo.Model.User;

public interface IUserDao {

	public User save(User user);

	public User findById(Long id);

	public User findByEmail(String email);

}
