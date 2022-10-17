package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;
import java.util.Set;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String index() {
        return "/auth/login";
    }

    @GetMapping("/currentUser")
    @ResponseBody
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userService.getUserById(((User) authentication.getPrincipal()).getId());
    }

    @GetMapping("/getUser/{id}")
    @ResponseBody
    public User getUser(@PathVariable(value = "id") long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/getUsers")
    @ResponseBody
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/user")
    public String userDetails() {
        return "user/index";
    }

    @GetMapping("/admin")
    public String printUsers(@ModelAttribute(value = "userModel") User userModel, @ModelAttribute(value = "role") Role role, ModelMap model) {
        return "admin/index";
    }

    @PostMapping("/api/admin/delete/")
    @ResponseBody
    public User deleteUser(@RequestBody User user) {
        System.out.println(user.toString());
        userService.deleteUser(user.getId());
        return null;
    }

    @PostMapping("/api/admin/adduser")
    @ResponseBody
    public User addUser(@RequestBody User user) {
        userService.addUser(user);
        return user;
    }

    @PostMapping("/api/admin/edit/")
    @ResponseBody
    public User editUser(@RequestBody User user) {
        userService.updateUser(user);
        return user;
    }

}
