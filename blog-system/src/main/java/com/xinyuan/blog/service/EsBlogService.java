package com.xinyuan.blog.service;

import com.xinyuan.blog.domain.EsBlog;
import com.xinyuan.blog.domain.User;
import com.xinyuan.blog.vo.TagVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EsBlogService {
    void removeEsBlog(String id);

    EsBlog updateEsBlog(EsBlog esBlog);

    EsBlog getEsBlogByBlogId(Long blogId);

    /**
     * get the newest pages of es blogs
     * @param keyword
     * @param pageable
     * @return Page of esblogs
     */
    Page<EsBlog> listNewestEsBlogs(String keyword, Pageable pageable);

    /**
     * get the hotest pages of es blogs
     * @param keyword
     * @param pageable
     * @return page of elblogs
     */
    Page<EsBlog> listHotestEsBlogs(String keyword, Pageable pageable);

    Page<EsBlog> listEsBlogs(Pageable pageable);

    /**
     * list newest 5 blogs
     * @return list of esblogs
     */
    List<EsBlog> listTop5NewestEsBlogs();

    /**
     * list hotest 5 blogs
     * @return list of esblogs
     */
    List<EsBlog> listTop5HotestEsBlogs();

    List<TagVO> listTop30Tags();

    List<User> listTop12Users();
}
