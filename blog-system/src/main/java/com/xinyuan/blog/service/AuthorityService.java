package com.xinyuan.blog.service;

import com.xinyuan.blog.domain.Authority;

/**
 * Interface of Authority Service
 * @since 2018.2.21
 * @author Xinyuan Gui
 */
public interface AuthorityService {
    /**
     * Get the authority by corresponding id
     * @param id
     * @return authority
     */
    Authority getAuthorityById(Long id);
}
