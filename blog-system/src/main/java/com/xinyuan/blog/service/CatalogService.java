package com.xinyuan.blog.service;

import com.xinyuan.blog.domain.Catalog;
import com.xinyuan.blog.domain.User;
import org.springframework.context.annotation.Bean;

import java.util.List;

public interface CatalogService {
    Catalog saveCatalog(Catalog catalog);

    void removeCatalog(Long id);

    String getUnsortedCatalogName();

    Catalog getCatalogById(Long id);

    List<Catalog> listCatalogs(User user);

    /**
     * Every user has one unsorted catalog
     * This catalog cannot be deleted
     * Every blog without any catalog will be classified to this catalog
     * */
    Catalog saveUnsortedCatalogIfNotExist(User user);

    Catalog getUnsortedCatalog(User user);
}
