package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDao {

    void addUser(User user);

    User getUserById(Long id);

    List<User> getUsers();

    void updateUser(User user);

    void deleteUser(Long id);
}
