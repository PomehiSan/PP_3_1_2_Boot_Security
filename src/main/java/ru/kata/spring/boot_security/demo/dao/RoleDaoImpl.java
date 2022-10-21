package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addRole(Role role) {
        if (!getRoles().contains(role)) {
            entityManager.persist(role);
        }
    }

    @Override
    public List<Role> getRoles() {
        return entityManager.createQuery("select u from Role u").getResultList();
    }

    @Override
    public Role getRoleByName(String role) {
        try {
            return (Role) entityManager.createQuery("select r from Role r where r.name = :name")
                    .setParameter("name", role).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

    }
}
