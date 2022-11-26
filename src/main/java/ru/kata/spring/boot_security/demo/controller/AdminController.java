package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {
    
    private UserService userService;

    private RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;

    }

    @GetMapping("/allUsers")
    public String allUsers(Model model, Principal principal) {
        model.addAttribute("userAdmin", userService.loadUserByUsername(principal.getName()));
        List<User> user = userService.getListUsers();
        model.addAttribute("newUser", new User());
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.getListRoles());


        return "allUsers";
    }

    @PostMapping(value = "/add")
    public String createUser(@ModelAttribute("user") User user, @RequestParam(value = "role") String role) {
        if (role.equals("ROLE_USER")) {
            user.setRoles(List.of(roleService.getRoleById(2)));
        } else if (role.equals("ROLE_ADMIN")) {
            user.setRoles(List.of(roleService.getRoleById(1)));
        }

        userService.add(user);

        return "redirect:/admin/allUsers";
    }


    @PatchMapping(value = "/edit/{id}")
    public String userUpdate(@ModelAttribute("user") User user, @RequestParam(value = "role") String role) {

        if (role.equals("ROLE_USER")) {
            user.setRoles(List.of(roleService.getRoleById(2)));
        } else if (role.equals("ROLE_ADMIN")) {
            user.setRoles(List.of(roleService.getRoleById(1)));
        }
        userService.update(user);
        return "redirect:/admin/allUsers";
    }
     /*@PatchMapping("/edit/{id}")
    public String updateUser(@ModelAttribute("user") User user, @RequestParam(value = "role") String role) {
        user.setRoles(Set.of(roleService.getRoleByName(role)));
        userService.editUser(user);
        return "redirect:/admin/allUsers";
    }*/


    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.delete(id);
        return "redirect:/admin/allUsers";
    }
}

