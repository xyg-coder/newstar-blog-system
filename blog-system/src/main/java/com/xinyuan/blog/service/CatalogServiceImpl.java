package com.xinyuan.blog.service;

import com.xinyuan.blog.domain.Blog;
import com.xinyuan.blog.domain.Catalog;
import com.xinyuan.blog.domain.User;
import com.xinyuan.blog.repository.BlogRepository;
import com.xinyuan.blog.repository.CatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogServiceImpl implements CatalogService {
    @Autowired
    private CatalogRepository catalogRepository;

    @Autowired
    private BlogRepository blogRepository;

    @Override
    public String getUnsortedCatalogName() {
        return "Unsorted Catalog";
    }

    @Override
    public Catalog saveCatalog(Catalog catalog) {
        List<Catalog> catalogs = catalogRepository.findByUserAndAndName(catalog.getUser(), catalog.getName());
        if (catalogs != null && !catalogs.isEmpty()) {
            throw new IllegalArgumentException("This catalog already exists");
        }
        return catalogRepository.save(catalog);
    }

    /**
     * move all the blogs in the removed category to the default category
     * */
    @Override
    public void removeCatalog(Long id) {
        // we cannot delete the unsorted catalog
        Catalog catalog = catalogRepository.findOne(id);
        if (catalog.getName().equals(getUnsortedCatalogName())) {
            throw new IllegalArgumentException("Cannot delete default catalog");
        }
        // move all the blogs in the removed catagory to the default category
        List<Blog> blogs = blogRepository.findAllByCatalog(catalog);
        for (Blog blog : blogs) {
            blog.setCatalog(getUnsortedCatalog(catalog.getUser()));
            blogRepository.save(blog);
        }

        catalogRepository.delete(id);
    }

    @Override
    public Catalog getCatalogById(Long id) {
        return catalogRepository.findOne(id);
    }

    @Override
    public List<Catalog> listCatalogs(User user) {
        return catalogRepository.findByUser(user);
    }

    @Override
    public Catalog saveUnsortedCatalogIfNotExist(User user) {
        List<Catalog> catalogList = catalogRepository.findByUserAndAndName(user, getUnsortedCatalogName());
        if (catalogList != null && !catalogList.isEmpty()) {
            return catalogList.get(0);
        }
        Catalog defaultCatalog = new Catalog();
        defaultCatalog.setUser(user);
        defaultCatalog.setName(getUnsortedCatalogName());
        return saveCatalog(defaultCatalog);
    }

    @Override
    public Catalog getUnsortedCatalog(User user) {
        List<Catalog> catalogList = catalogRepository.findByUserAndAndName(user, getUnsortedCatalogName());
        if (catalogList == null || catalogList.size() != 1) {
            throw new RuntimeException("Unsorted Catalog should only exist one");
        }
        return catalogList.get(0);
    }
}
