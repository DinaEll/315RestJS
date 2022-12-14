package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class Init {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public Init(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void init() {
        Role role1 = new Role("ROLE_ADMIN");
        Role role2 = new Role("ROLE_USER");

        roleService.add(role1);
        roleService.add(role2);

        List<Role> roleAdmin = new ArrayList<>();
        List<Role> roleUser = new ArrayList<>();
        List<Role> allRoles = new ArrayList<>();

        allRoles.add(role1);
        allRoles.add(role2);
        roleAdmin.add(role1);
        roleUser.add(role2);

        User user1 = new User("admin", "admin", 12,  roleAdmin);
        User user2 = new User("Камышик", "Камышик", 2,  roleAdmin);
        User user3 = new User("Камышинка", "Камышинка", 3,  roleUser);
        User user4 = new User("admin2", "admin2", 2,  allRoles);
        User user5 = new User("Креветка", "Креветка", 2,  roleUser);

        userService.add(user1);
        userService.add(user2);
        userService.add(user3);
        userService.add(user4);
        userService.add(user5);
    }
}
