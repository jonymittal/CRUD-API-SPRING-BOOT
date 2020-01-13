package com.demo.DaoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.demo.Dao.IRoleDao;
import com.demo.Model.Role;

@Repository
@Transactional
public class RoleDao implements IRoleDao {

	@Autowired
	EntityManager entityManager;

	@Override
	public Role save(Role role) {
		return entityManager.merge(role);
	}

	@Override
	public Role findById(Integer id) {
		return entityManager.find(Role.class, id);
	}

	@Override
	public List<Role> getRoles() {
		return entityManager.createQuery("from Role", Role.class).getResultList();
	}
}
