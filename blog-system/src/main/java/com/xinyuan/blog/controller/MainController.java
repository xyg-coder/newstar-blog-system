package com.xinyuan.blog.controller;

import com.xinyuan.blog.domain.Authority;
import com.xinyuan.blog.domain.User;
import com.xinyuan.blog.service.AuthorityService;
import com.xinyuan.blog.service.UserService;
import com.xinyuan.blog.util.ConstraintViolationExceptionHandler;
import com.xinyuan.blog.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * main page controller
 * @since 1.0.0 2018.2.10
 * @author <a href="https://github.com/xinyuangui">Xinyuan Gui</a>
 * */
@Controller
public class MainController {
    // the id of blog-user role
    private final static long ROLE_USER_AUTHORITY_ID = 2L;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorityService authorityService;

    @GetMapping("/")
    public String root() {
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String index() {
        return "redirect:/blogs";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        model.addAttribute("errorMsg", "Login failed, incompatible username and password");
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public ResponseEntity<Response> register(User user) {
        System.out.println("register post gets called");
        List<Authority> authorityList = new ArrayList<>();
        authorityList.add(authorityService.getAuthorityById(ROLE_USER_AUTHORITY_ID));
        user.setAuthorities(authorityList);

        try {
            userService.saveUser(user);
        } catch (ConstraintViolationException e) {
            return ResponseEntity.ok().body(new Response(false,
                    ConstraintViolationExceptionHandler.getMessage(e)));
        }

        return ResponseEntity.ok().body(new Response(true, "Success", user));
    }

    @GetMapping("/search")
    public String search() {
        return "search";
    }
}
