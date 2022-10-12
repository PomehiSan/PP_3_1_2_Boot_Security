package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public String userDetails(ModelMap model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        model.put("user", user);
        return "user/index";
    }

    @GetMapping("/admin")
    public String printUsers(@ModelAttribute(value = "user") User user, ModelMap model) {
        model.put("users", userService.getUsers());
        return "admin/index";
    }

    @PostMapping("/admin/delete/{id}")
    public String deleteUser(@PathVariable(value = "id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/index";
    }

    @PostMapping("/admin/adduser")
    public String addUser(User user) {
        userService.addUser(user);
        return "redirect:/admin/index";
    }

    @GetMapping("/admin/edit/{id}")
    public String editUser(@PathVariable(value = "id") Long id, ModelMap model) {
        model.addAttribute("user", userService.getUserById(id));
        return "admin/edit";
    }

    @PutMapping("/admin/edit/{id}")
    public String editUser(@PathVariable(value = "id") Long id, @ModelAttribute User user) {
        User userNew = userService.getUserById(id);
        userNew.setName(user.getName());
        userNew.setSurname(user.getSurname());
        userNew.setOld(user.getOld());
        userService.updateUser(userNew);
        return "redirect:/admin";
    }

}
