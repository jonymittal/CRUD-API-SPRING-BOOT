package com.demo.Config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.demo.Model.Role;
import com.demo.Service.IRoleService;

@Component
public class DatabaseLoader implements CommandLineRunner {

	@Autowired
	private IRoleService roleService;

	@Override
	public void run(String... args) throws Exception {
		addRoles();
	}

	void addRoles() {
		List<Role> roles = roleService.getRoles();
		if (roles == null || roles.size() == 0) {
			roleService.save(new Role("ROLE_ADMIN"));
			roleService.save(new Role("ROLE_USER"));
		}
	}

}
