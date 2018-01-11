package com.codeup.adlister.dao;

import com.codeup.adlister.models.User;

import java.util.List;

public interface Users {
    User findByUsername(String username);
    Long insert(User user);
    User findById(Long id);
    void updateUser(User user);
    void updatePassword(User user);
    void deleteUser(User user);
}
