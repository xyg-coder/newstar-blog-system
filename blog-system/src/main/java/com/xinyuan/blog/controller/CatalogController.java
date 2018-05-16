package com.xinyuan.blog.controller;

import com.xinyuan.blog.domain.Catalog;
import com.xinyuan.blog.domain.User;
import com.xinyuan.blog.service.CatalogService;
import com.xinyuan.blog.util.ConstraintViolationExceptionHandler;
import com.xinyuan.blog.vo.CatalogVO;
import com.xinyuan.blog.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.List;

@Controller
@RequestMapping("/catalogs")
public class CatalogController {
    @Autowired
    private CatalogService catalogService;

    @Autowired
    private UserDetailsService userDetailsService;

    @GetMapping
    public String listCatalogs(@RequestParam(value = "username", required = true) String username, Model model) {
        User user = (User)userDetailsService.loadUserByUsername(username);
        List<Catalog> catalogs = catalogService.listCatalogs(user);

        boolean isOwner = false;

        if (SecurityContextHolder.getContext().getAuthentication() !=null && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
                &&  !SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser")) {
            User principal = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal !=null && user.getUsername().equals(principal.getUsername())) {
                isOwner = true;
            }
        }

        model.addAttribute("isCatalogOwner", isOwner);
        model.addAttribute("catalogs", catalogs);
        model.addAttribute("unsortedCatalogName", catalogService.getUnsortedCatalogName());
        return "userspace/u :: #catalogReplace";
    }

    /**
     * create catalog
     * @param catalogVO
     * */
    @PostMapping
    @PreAuthorize("authentication.name.equals(#catalogVO.username)")
    public ResponseEntity<Response> create(@RequestBody CatalogVO catalogVO) {
        String username = catalogVO.getUsername();
        Catalog catalog = catalogVO.getCatalog();

        User user = (User)userDetailsService.loadUserByUsername(username);

        try {
            catalog.setUser(user);
            catalogService.saveCatalog(catalog);
        } catch (ConstraintViolationException e) {
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }

        return ResponseEntity.ok().body(new Response(true, "Success", null));
    }

    /**
     * delete catalog
     * */
    @DeleteMapping("/{id}")
    @PreAuthorize("authentication.name.equals(#username)")
    public ResponseEntity<Response> delete(String username, @PathVariable("id") Long id) {
        try {
            catalogService.removeCatalog(id);
        } catch (ConstraintViolationException e) {
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }

        return ResponseEntity.ok().body(new Response(true, "Success", null));
    }

    @GetMapping("/edit")
    public String getCatalogEdit(Model model) {
        Catalog catalog = new Catalog(null, null);
        model.addAttribute("catalog", catalog);
        return "userspace/catalogedit";
    }

    @GetMapping("/edit/{id}")
    public String editCatalogById(@PathVariable("id") Long id, Model model) {
        Catalog catalog = catalogService.getCatalogById(id);
        if (catalog.getName().equals(catalogService.getUnsortedCatalogName())) {
            throw new IllegalArgumentException("Should not edit the default catalog");
        }
        model.addAttribute("catalog", catalog);
        return "userspace/catalogedit";
    }
}
