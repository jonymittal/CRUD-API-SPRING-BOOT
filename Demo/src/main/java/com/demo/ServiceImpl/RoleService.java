package com.demo.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.Dao.IRoleDao;
import com.demo.Model.Role;
import com.demo.Service.IRoleService;

@Service
public class RoleService implements IRoleService {
	
	
	@Autowired
	IRoleDao roleDao;
	
	@Override
	public Role save(Role role) {
		return roleDao.save(role);
	}

	@Override
	public Role findById(Integer id) {
		return roleDao.findById(id);
	}

	@Override
	public List<Role> getRoles() {
		return roleDao.getRoles();
	}
}
