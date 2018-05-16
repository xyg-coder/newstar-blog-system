package com.xinyuan.blog.repository;

import com.xinyuan.blog.domain.Blog;
import com.xinyuan.blog.domain.Catalog;
import com.xinyuan.blog.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Blog repository interface
 */
public interface BlogRepository extends JpaRepository<Blog, Long> {
    /**
     * get blogs by user and title like search, in the descending order of the create time
     * @param user
     * @param title
     * @param pageable
     * @return
     */
    Page<Blog> findByUserAndTitleLikeOrderByCreateTimeDesc(User user, String title, Pageable pageable);

    /**
     * get blogs by the user and the title like
     * @param user
     * @param title
     * @return
     */
    Page<Blog> findByUserAndTitleLike(User user, String title, Pageable pageable);


    @Query("select b from Blog b where b.user = ?1 and b.title like ?2 or b.user = ?1 and b.summary like ?3 or b.user = ?1 and b.tags like ?4 order by b.createTime desc ")
    Page<Blog> findByUserAndTitleLikeOrUserAndSummaryLikeOrUserAndTagsLikeOrderByCreateTimeDesc(User user, String title, String summary, String tags, Pageable pageable);

    Page<Blog> findByCatalog(Catalog catalog, Pageable pageable);

    List<Blog> findAllByCatalog(Catalog catalog);
}
