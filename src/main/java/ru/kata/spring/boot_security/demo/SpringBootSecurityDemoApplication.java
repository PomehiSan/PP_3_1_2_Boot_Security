package ru.kata.spring.boot_security.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.service.RoleService;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class SpringBootSecurityDemoApplication {

	@Autowired
	RoleService roleService;

	@PostConstruct
	public void addMainRoles() {
		if (roleService.getRoles().isEmpty()) {
			roleService.addRole(new Role("ROLE_USER"));
			roleService.addRole(new Role("ROLE_ADMIN"));
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSecurityDemoApplication.class, args);
	}

}
