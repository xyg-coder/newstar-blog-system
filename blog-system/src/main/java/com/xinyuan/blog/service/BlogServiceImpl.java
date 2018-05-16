package com.xinyuan.blog.service;

import com.xinyuan.blog.domain.*;
import com.xinyuan.blog.repository.BlogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private EsBlogService esBlogService;


    @Override
    @Transactional
    public Blog saveBlog(Blog blog) {
        Blog returnBlog = blogRepository.save(blog);
        EsBlog esBlog = esBlogService.getEsBlogByBlogId(blog.getId());
        if (esBlog == null) {
            esBlog = new EsBlog(blog);
        } else {
            esBlog.update(blog);
        }
        esBlogService.updateEsBlog(esBlog);
        return returnBlog;
    }

    @Override
    @Transactional
    public void removeBlog(Long id) {
        blogRepository.delete(id);
        esBlogService.removeEsBlog(esBlogService.getEsBlogByBlogId(id).getId());
    }

    @Override
    public Blog getBlogById(Long id) {
        return blogRepository.findOne(id);
    }

    @Override
    public Page<Blog> listBlogsByTitleLike(User user, String title, Pageable pageable) {
        // like search
        title = "%" + title + "%";
        Page<Blog> blogs = blogRepository.findByUserAndTitleLike(user, title, pageable);
        return blogs;
    }

    @Override
    public Page<Blog> listBlogsByTitleLikeAndSort(User user, String title, Pageable pageable) {
        title = "%" + title + "%";
        return blogRepository.findByUserAndTitleLike(user, title, pageable);
    }

    @Override
    public Page<Blog> listBlogsByTitleLikeOrSummaryLike(User user, String title, String summary, String tags, Pageable pageable) {
        title = "%" + title + "%";
        summary = "%" + summary + "%";
        tags = "%" + tags +  "%";
        return blogRepository.findByUserAndTitleLikeOrUserAndSummaryLikeOrUserAndTagsLikeOrderByCreateTimeDesc(user, title, summary, tags, pageable);
    }

    @Override
    @Transactional
    public void readIncrease(Long id) {
        Blog blog = blogRepository.findOne(id);
        blog.setReading(blog.getReading() + 1);
        blogRepository.save(blog);
        EsBlog esBlog = esBlogService.getEsBlogByBlogId(id);
        esBlog.update(blog);
        esBlogService.updateEsBlog(esBlog);
    }

    @Override
    public Blog createComment(Long blogId, String commentContent) {
        Blog originalBlog = blogRepository.findOne(blogId);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Comment comment = new Comment(commentContent, user);
        originalBlog.addComment(comment);
        EsBlog esBlog = esBlogService.getEsBlogByBlogId(blogId);
        esBlog.update(originalBlog);
        esBlogService.updateEsBlog(esBlog);
        return blogRepository.save(originalBlog);
    }

    @Override
    public void removeComment(Long blogId, Long commentId) {
        Blog originalBlog = blogRepository.findOne(blogId);
        originalBlog.removeComment(commentId);
        blogRepository.save(originalBlog);
        EsBlog esBlog = esBlogService.getEsBlogByBlogId(blogId);
        esBlog.update(originalBlog);
        esBlogService.updateEsBlog(esBlog);
    }

    @Override
    public Blog createVote(Long blogId) {
        Blog originalBlog = blogRepository.findOne(blogId);
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Vote vote = new Vote(user);
        if (originalBlog.addVote(vote)) {
            throw new IllegalArgumentException("This user already voted");
        }
        EsBlog esBlog = esBlogService.getEsBlogByBlogId(blogId);
        esBlog.update(originalBlog);
        esBlogService.updateEsBlog(esBlog);
        return blogRepository.save(originalBlog);
    }

    @Override
    public void removeVote(Long blogId, Long voteId) {
        Blog originalBlog = blogRepository.findOne(blogId);
        originalBlog.removeVote(voteId);
        blogRepository.save(originalBlog);
        EsBlog esBlog = esBlogService.getEsBlogByBlogId(blogId);
        esBlog.update(originalBlog);
        esBlogService.updateEsBlog(esBlog);
    }

    @Override
    public Page<Blog> listBlogsByCatalog(Catalog catalog, Pageable pageable) {
        return blogRepository.findByCatalog(catalog, pageable);
    }
}
