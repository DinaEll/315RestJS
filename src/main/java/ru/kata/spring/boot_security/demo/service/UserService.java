package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<Role> getAllRoles();
    List<Role> getListByRole(List<String> name);
    void add(User user);
    List<User> getAllUsers();
    void delete(int id);
    void update(User user);
    User getById(int id);
    User getByUsername(String userName);
}
