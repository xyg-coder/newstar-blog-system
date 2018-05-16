package com.xinyuan.blog.controller;

import com.xinyuan.blog.vo.Menu;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * Administor controller
 * @since 1.0.0 2018.2.10
 * @author <a href="https://github.com/xinyuangui">Xinyuan Gui</a>
 * */
@Controller
@RequestMapping("/admins")
public class AdminController {

    /**
     * obtain management main page
     * @param model
     * @return
     */
    @GetMapping
    public ModelAndView listUsers(Model model) {
        List<Menu> list = new ArrayList<>();
        list.add(new Menu("User Management", "/users"));
        model.addAttribute("list", list);

        return new ModelAndView("admins/index", "menuList", model);
    }
}
