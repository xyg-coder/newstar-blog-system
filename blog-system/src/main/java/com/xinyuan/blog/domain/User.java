package com.xinyuan.blog.domain;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * User entity
 * since 1.0.0 2018
 * author: Xinyuan Gui
 * */
@Entity
//@Table(name = "user")
public class User implements Serializable,UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "username cannot be empty")
    @Size(min = 3)
    @Column(unique = true)
    private String username;

    @NotEmpty(message = "email cannot be empty")
    @Size(max = 50)
    @Email(message = "wrong email style")
    @Column(length = 50, nullable = false)
    private String email; // use email as the login account

    @NotEmpty(message = "name cannot be null")
    @Size(max = 20)
    @Column(length = 20, nullable = false)
    private String name;

    @NotEmpty(message = "Password cannot be null")
    @Size(max = 100)
    @Column(length = 100)
    private String password;

    @Size(max = 200)
    private String avatar; // url of the avatar

    @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_authority",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id")
    )
    private List<Authority> authorities;

    protected User() {
    }

    public User(String username, String email, String name, String password, String avatar) {
        this.username = username;
        this.email = email;
        this.name = name;
        this.password = password;
        this.avatar = avatar;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEncodePassword(String password) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodePassword = encoder.encode(password);
        this.password = encodePassword;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * get the collection of the authorities
     * @return List of SimpleGrantedAuthority
     */
    public List<? extends GrantedAuthority> getAuthorities() {
        // use SimpleGratedAuthority because it has role string
//        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
//        for (GrantedAuthority authority : this.authorities) {
//            simpleGrantedAuthorities.add(new SimpleGrantedAuthority(authority.getAuthority()));
//        }
//        return simpleGrantedAuthorities;
        return this.authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
//        return "User{" +
//                "id=" + id +
//                ", username='" + username + '\'' +
//                ", email='" + email + '\'' +
//                ", password='" + password + '\'' +
//                ", avatar='" + avatar + '\'' +
//                '}';
        return String.format("User[id=%d, username='%s', email='%s', password='%s']", id, username, email,
                password);
    }
}
