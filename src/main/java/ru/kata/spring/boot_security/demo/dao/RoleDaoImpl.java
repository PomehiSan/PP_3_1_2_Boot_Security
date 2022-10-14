package ru.kata.spring.boot_security.demo.dao;

import org.hibernate.HibernateException;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.SQLException;
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
        return (Role) entityManager.createQuery("select r from Role r where r.name = :name")
                .setParameter("name", role).getSingleResult();
    }
}
