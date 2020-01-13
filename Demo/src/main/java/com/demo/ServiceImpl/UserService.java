package com.demo.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.Dao.IRoleDao;
import com.demo.Dao.IUserDao;
import com.demo.Model.User;
import com.demo.Service.IUserService;

@Service
public class UserService implements IUserService {

	@Autowired
	IUserDao userDao;
	
	@Autowired
	IRoleDao roleDao;

	@Override
	public User save(User user) {
		return userDao.save(user);
	}

	@Override
	public User findById(Long id) {
		return userDao.findById(id);
	}

	@Override
	public User findByEmail(String email) {
		return userDao.findByEmail(email);
	}

	@Override
	public User mergeAsNew(User user) {
		user.setEmail(user.getEmail());
		user.setPassword(user.getPassword());
		user.setRole(roleDao.findById(2));
		return userDao.save(user);
	}

}
