package ru.kata.spring.boot_security.demo.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserDetailServiceImpl;

import java.util.Optional;

@Component
public class UserValidator implements Validator {

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        Optional<User> findUser = userDetailService.containsUserByUsername(user.getUsername());

        if (!findUser.isEmpty()) {
            errors.rejectValue("username", "", "такой логин уже есть");
        }
    }
}
