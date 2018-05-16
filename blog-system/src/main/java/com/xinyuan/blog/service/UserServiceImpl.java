package com.xinyuan.blog.service;

import com.xinyuan.blog.domain.User;
import com.xinyuan.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * User service implementation
 * @since 1.0.0 2018.2.12
 * @author guixinyuan
 */
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CatalogService catalogService;

    // transactional: used to easy the operation on the data
    @Override
    @Transactional
    public User saveUser(User user) {
        User newUser = userRepository.save(user);
        catalogService.saveUnsortedCatalogIfNotExist(user);
        return newUser;
    }

    @Override
    @Transactional
    public void removeUser(Long id) {
        userRepository.delete(id);
    }

    @Override
    @Transactional
    public void removeUsersInBatch(List<User> users) {
        userRepository.delete(users);
    }

    @Override
    @Transactional
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @Override
    public Page<User> listUsersByUsernameLike(String username, Pageable pageable) {
        username = "%" + username + "%";
        Page<User> users = userRepository.findByUsernameLike(username, pageable);
        return users;
    }

    @Override
    public List<User> listUsersByUsernames(Collection<String> usernamelist) {
        return userRepository.findByUsernameIn(usernamelist);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username);
    }
}
