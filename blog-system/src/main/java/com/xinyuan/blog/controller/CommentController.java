package com.xinyuan.blog.controller;

import com.xinyuan.blog.domain.Blog;
import com.xinyuan.blog.domain.Comment;
import com.xinyuan.blog.domain.User;
import com.xinyuan.blog.service.BlogService;
import com.xinyuan.blog.service.CommentService;
import com.xinyuan.blog.util.ConstraintViolationExceptionHandler;
import com.xinyuan.blog.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * Comment api controller
 * @since 1.0.0 2018.3.4
 * @author Xinyuan Gui
 */
@Controller
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private BlogService blogService;

    @Autowired
    private CommentService commentService;

    /**
     * Get the comment list
     * @param blogId
     * @param model
     * @return
     */
    @GetMapping
    public String listComments(@RequestParam(value = "blogId", required = true) Long blogId, Model model) {
        Blog blog = blogService.getBlogById(blogId);
        List<Comment> comments = blog.getComments();

        // only the blog owner and the comment owner can delete the comment
        String viewUsername = "";
        if (SecurityContextHolder.getContext().getAuthentication() != null
                && SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
                !SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser")) {
            User principal = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal != null) {
                viewUsername = principal.getUsername();
            }
        }
        model.addAttribute("viewer", viewUsername);
        model.addAttribute("comments", comments);
        model.addAttribute("blog", blog);
        return "userspace/blog :: #mainContainerReplace";
    }

    /**
     * Post comments
     * @param blogId
     * @param commentContent
     * @return
     */
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Response> createComment(Long blogId, String commentContent) {
        try {
            blogService.createComment(blogId, commentContent);
        } catch (ConstraintViolationException e) {
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }

        return ResponseEntity.ok().body(new Response(true, "Success", null));
    }

    /**
     * Delete comments
     * @param id
     * @param blogId
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteBlog(@PathVariable("id") Long id, Long blogId) {
        boolean hasAuthToDelete = false;
        User blogOwner = blogService.getBlogById(blogId).getUser();
        User commentOwner = commentService.getCommentById(id).getUser();

        if (SecurityContextHolder.getContext().getAuthentication() != null &&
                !SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser")) {
            User principal = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal != null &&
                    (blogOwner.getUsername().equals(principal.getUsername()) ||
                    commentOwner.getUsername().equals(principal.getUsername()))) {
                hasAuthToDelete = true;
            }
        }
        if (!hasAuthToDelete) {
            return ResponseEntity.ok().body(new Response(false, "No Authentication"));
        }

        try {
            blogService.removeComment(blogId, id);
            commentService.removeComment(id);
        } catch (ConstraintViolationException e) {
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }

        return ResponseEntity.ok().body(new Response(true, "Success", null));
    }
}
