package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@RestController
public class UserRestController {

    private UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/currentUser")
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userService.getUserById(((User) authentication.getPrincipal()).getId());
    }

    @GetMapping("/getUser/{id}")
    public User getUser(@PathVariable(value = "id") long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/getUsers")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @PostMapping("/api/admin/delete/")
    public User deleteUser(@RequestBody User user) {
        System.out.println(user.toString());
        userService.deleteUser(user.getId());
        return null;
    }

    @PostMapping("/api/admin/adduser")
    public User addUser(@RequestBody User user) {
        userService.addUser(user);
        return user;
    }

    @PostMapping("/api/admin/edit/")
    public User editUser(@RequestBody User user) {
        userService.updateUser(user);
        return user;
    }
}
