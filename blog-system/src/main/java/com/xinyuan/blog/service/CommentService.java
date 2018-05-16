package com.xinyuan.blog.service;

import com.xinyuan.blog.domain.Comment;

/**
 * comment service interface
 * @since 1.0.0 2018.3.4
 * @author Xinyuan Gui
 */
public interface CommentService {
    Comment getCommentById(Long id);

    void removeComment(Long id);
}
