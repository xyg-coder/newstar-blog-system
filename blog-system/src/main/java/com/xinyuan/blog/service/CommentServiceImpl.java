package com.xinyuan.blog.service;

import com.xinyuan.blog.domain.Comment;
import com.xinyuan.blog.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Comment service implementation
 * @since 1.0.0 2018.3.4
 * @author Xinyuan Gui
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Comment getCommentById(Long id) {
        return commentRepository.getOne(id);
    }

    @Override
    @Transactional
    public void removeComment(Long id) {
        commentRepository.delete(id);
    }
}
