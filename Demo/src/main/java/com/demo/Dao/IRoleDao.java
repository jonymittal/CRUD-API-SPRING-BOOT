package com.demo.Dao;

import java.util.List;

import com.demo.Model.Role;

public interface IRoleDao {
	
	public Role save(Role role);

	public Role findById(Integer id);
	
	public List<Role> getRoles();

}
