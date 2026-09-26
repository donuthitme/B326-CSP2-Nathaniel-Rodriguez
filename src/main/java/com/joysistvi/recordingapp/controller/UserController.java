package com.joysistvi.recordingapp.controller;

import com.joysistvi.recordingapp.model.User;
import com.joysistvi.recordingapp.service.UserService;

import java.util.List;

public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    public User getUserById(int id) {
        return userService.getUserById(id);
    }

    public User getUserByUsername(String username) {
        return userService.getUserByUsername(username);
    }

    public boolean createUser(String username, String password) {
        return userService.createUser(new User(username, password));
    }

    public boolean updateUser(int id, String username, String password) {
        return userService.updateUser(new User(id, username, password));
    }

    public boolean deleteUser(int id) {
        return userService.deleteUser(id);
    }
}