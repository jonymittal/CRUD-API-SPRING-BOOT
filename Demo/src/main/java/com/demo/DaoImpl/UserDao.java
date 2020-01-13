package com.demo.DaoImpl;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.demo.Dao.IUserDao;
import com.demo.Model.User;

@Repository
@Transactional
public class UserDao implements IUserDao {

	@Autowired
	EntityManager entityManager;

	@Override
	public User save(User user) {
		return entityManager.merge(user);
	}

	@Override
	public User findById(Long id) {
		return entityManager.find(User.class, id);
	}

	@Override
	public User findByEmail(String email) {
		try {
			return entityManager.createQuery("from User where email like'" + email + "'", User.class).getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
}
