package com.joysistvi.recordingapp.service;

import com.joysistvi.recordingapp.model.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();
    User getUserById(int id);
    User getUserByUsername(String username);
    boolean createUser(User user);
    boolean updateUser(User user);
    boolean deleteUser(int id);

    User login(String username, String password);
    boolean register(String username, String password);
}