package com.xinyuan.blog.service;

import com.xinyuan.blog.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;

/**
 * User service interface
 * @since 1.0.0 2018.2.12
 * @author guixinyuan
 */
public interface UserService {
    /**
     * save user
     * @param user
     * @return
     */
    public User saveUser(User user);

    /**
     * delete user
     * @param id
     */
    public void removeUser(Long id);

    /**
     * remove users in the list
     * @param users
     */
    public void removeUsersInBatch(List<User> users);

    /**
     * update user information
     * @param user
     * @return
     */
    public User updateUser(User user);

    /**
     * return user with specified id
     * @param id
     * @return
     */
    public User getUserById(Long id);

    /**
     * return all the users
     * @return
     */
    public List<User> listUsers();

    /**
     * get
     * @param username
     * @param pageable
     * @return
     */
    public Page<User> listUsersByUsernameLike(String username, Pageable pageable);

    List<User> listUsersByUsernames(Collection<String> usernamelist);
}
