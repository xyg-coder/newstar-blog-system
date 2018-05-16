package com.xinyuan.blog.repository;

import com.xinyuan.blog.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

/**
 * User repository
 *
 * @since 1.0.0 2018.2.12
 * @author guixinyuan
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Page<User>findByUsernameLike(String username, Pageable pageable);

    User findUserByEmail(String email);

    User findUserByUsername(String username);

    List<User> findByUsernameIn(Collection<String> usernames);
}
