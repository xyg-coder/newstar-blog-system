package com.xinyuan.blog.repository;

import com.xinyuan.blog.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Comment repository
 * @since 1.0.0 2018.3.4
 * @author Xinyuan Gui
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
