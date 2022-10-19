package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
public class UserController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public void setUserService(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/")
    public String index() {
        return "/auth/login";
    }

    @GetMapping("/user")
    public String userDetails(ModelMap model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        model.put("user", user);
        return "user/index";
    }

    @GetMapping("/admin")
    public String printUsers(@ModelAttribute(value = "userModel") User userModel, ModelMap model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        model.put("user", user);
        model.put("users", userService.getUsers());
        model.put("roles", roleService.getRoles());
        return "admin/index";
    }

    @PostMapping("/admin/delete/{id}")
    public String deleteUser(@PathVariable(value = "id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @PostMapping("/admin/adduser")
    public String addUser(User user) {
        userService.addUser(user);
        return "redirect:/admin/index";
    }

    @GetMapping("/admin/edit/{id}")
    public String editUser(@PathVariable(value = "id") Long id, ModelMap model) {
        model.addAttribute("userEdit", userService.getUserById(id));
        model.addAttribute("roles", roleService.getRoles());
        return "/admin/edit";
    }

    @PostMapping("/admin/edit/")
    public String editUser(@ModelAttribute(value = "userModel") User user) {
        System.out.println(user.getRole());
        userService.updateUser(user);
        return "redirect:/admin";
    }

}
