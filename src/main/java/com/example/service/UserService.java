package com.example.service;

import java.util.Optional;

import com.example.entity.User;

public interface UserService {

    void addUser(User user) throws Exception;

    Optional<User> getUser(long id);

    

}
