package ru.kata.spring.boot_security.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    @Column(name = "name")
    private String firstName;

    @NotEmpty(message = "Password should not be empty")
    @Column(name = "password")
    private String password;

    @Min(value = 0, message = "Age should be greater than 0")
    @Column(name = "age")
    private int age;


    @ManyToMany
    @JoinTable(name="users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;

    public void addRole(Role role) {
        this.roles.add(role);
        role.getUsers().add(this);
    }

    public void deleteRole(Role role) {
        this.roles.remove(role);
        role.getUsers().remove(this);
    }

    public User() {}

    public User(String userName, String password, int age, List<Role> roles) {
        this.firstName = userName;
        this.password = password;
        this.age = age;
        this.roles = roles;
    }

    public User(int id, String userName, String password, int age, List<Role> roles) {
        this.id = id;
        this.firstName = userName;
        this.password = password;
        this.age = age;
        this.roles = roles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String userName) {
        this.firstName = userName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return firstName;
    }


    public String getFirstName() {
        return firstName;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getShortRoles() {
        if (roles.toString().equals("[ROLE_USER]")) {
            return "USER";
        } else if (roles.toString().equals("[ROLE_ADMIN]")) {
            return "ADMIN";
        } else if (roles.equals(null)) {
            return null;
        }
        return "ADMIN USER";
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + firstName + '\'' +
                ", password='" + password + '\'' +
                ", age='" + age + '\'' +
                ", roles=" + getRoles() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && age == user.age && Objects.equals(firstName, user.firstName)
                && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, password, age);
    }
}