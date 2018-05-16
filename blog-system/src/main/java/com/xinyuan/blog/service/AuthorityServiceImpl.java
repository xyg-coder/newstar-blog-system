package com.xinyuan.blog.service;

import com.xinyuan.blog.domain.Authority;
import com.xinyuan.blog.repository.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Authority Service interface implementation
 * @since 2018.2.21
 * @author Xinyuan Gui
 */
@Service
public class AuthorityServiceImpl implements AuthorityService {
    @Autowired
    AuthorityRepository authorityRepository;

    @Override
    public Authority getAuthorityById(Long id) {
        return authorityRepository.findOne(id);
    }
}
