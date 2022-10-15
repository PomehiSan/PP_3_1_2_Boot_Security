package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.addRole(roleService.getRoleByName("ROLE_USER"));
        if (user.getRole() != null && !user.getRoles().contains(roleService.getRoleByName(user.getRole()))) {
            user.addRole(roleService.getRoleByName(user.getRole()));
        }
        userDao.addUser(user);
    }

    @Override
    public User getUserById(Long id) {
        return userDao.getUserById(id);
    }

    @Override
    public List<User> getUsers() {
        return userDao.getUsers();
    }

    @Override
    @Transactional
    public void updateUser(User newUser) {
        long id = newUser.getId();
        Role newRole = roleService.getRoleByName(newUser.getRole());
        User currentUser = userDao.getUserById(id);
        Set<Role> currentUserRoles = currentUser.getRoles();

        if (newUser.getPassword() == null || newUser.getPassword().isEmpty()) {
            newUser.setPassword(currentUser.getPassword());
        } else {
            newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        }

        if (!currentUserRoles.contains(newRole)) {
            currentUserRoles.add(newRole);
            newUser.setRoles(currentUserRoles);
        } else if (newRole == roleService.getRoleByName("ROLE_USER")) {
            currentUserRoles.clear();
            currentUserRoles.add(newRole);
            newUser.setRoles(currentUserRoles);
        }

        userDao.updateUser(newUser);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userDao.deleteUser(id);
    }
}
