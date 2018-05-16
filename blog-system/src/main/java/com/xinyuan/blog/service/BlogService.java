package com.xinyuan.blog.service;

import com.xinyuan.blog.domain.Blog;
import com.xinyuan.blog.domain.Catalog;
import com.xinyuan.blog.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlogService {
    /**
     * Save blog to database
     * @param blog
     * @return
     */
    Blog saveBlog(Blog blog);

    /**
     * delete blog with specific id
     * @param id
     */
    void removeBlog(Long id);

    /**
     * Get blog with specific id
     * @param id
     * @return
     */
    Blog getBlogById(Long id);

    /**
     * get the page of blogs with title like
     * @param user
     * @param title
     * @param pageable
     * @return
     */
    Page<Blog> listBlogsByTitleLike(User user, String title, Pageable pageable);

    /**
     * get the page of blogs with title like and sort with creating time
     * @param user
     * @param title
     * @param pageable
     * @return
     */
    Page<Blog> listBlogsByTitleLikeAndSort(User user, String title, Pageable pageable);

    Page<Blog> listBlogsByTitleLikeOrSummaryLike(User user, String title, String summary, String tags, Pageable pageable);

    /**
     * increase the count of readings
     * @param id
     */
    void readIncrease(Long id);

    /**
     * Create the comment for specific blog
     * @param blogId
     * @param commentContent
     * @return
     */
    Blog createComment(Long blogId, String commentContent);

    /**
     * remove teh specific comment of specific blog
     * @param blogId
     * @param commentId
     */
    void removeComment(Long blogId, Long commentId);

    /**
     * add vote
     * */
    Blog createVote(Long blogId);

    /**
     * remove vote
     * */
    void removeVote(Long blogId, Long voteId);

    Page<Blog> listBlogsByCatalog(Catalog catalog, Pageable pageable);
}
