package com.xinyuan.blog.repository;

import com.xinyuan.blog.domain.Catalog;
import com.xinyuan.blog.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CatalogRepository extends JpaRepository<Catalog, Long> {

    List<Catalog> findByUser(User user);

    List<Catalog> findByUserAndAndName(User user, String name);
}
