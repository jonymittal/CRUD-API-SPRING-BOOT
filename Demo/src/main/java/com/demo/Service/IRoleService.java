package com.demo.Service;

import java.util.List;

import com.demo.Model.Role;

public interface IRoleService {
	
	public Role save(Role role);

	public Role findById(Integer id);
	
	public List<Role> getRoles();
}
