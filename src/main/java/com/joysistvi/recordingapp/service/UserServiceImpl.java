package com.joysistvi.recordingapp.service;

import com.joysistvi.recordingapp.model.User;
import com.joysistvi.recordingapp.repository.UserRepo;

import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    // getAllUsers
    @Override
    public List<User> getAllUsers() {
        return userRepo.getAllUsers();
    }

    // getUserById
    @Override
    public User getUserById(int id) {
        if (id <= 0) {
            System.out.println("Invalid User ID");
            return null;
        }
        User user = userRepo.getUserById(id);
        if (user == null) {
            System.out.println("User not found");
        }
        return user;
    }

    // getUserByUsername
    @Override
    public User getUserByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            System.out.println("Username cannot be empty.");
            return null;
        }
        User user = userRepo.getUserByUsername(username.trim());
        if (user == null) {
            System.out.println("User not found");
        }
        return user;
    }

    // createUser
    @Override
    public boolean createUser(User user) {
        if (user == null) {
            System.out.println("User object cannot be null.");
            return false;
        }
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            System.out.println("Username is required");
            return false;
        }
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            System.out.println("Password is required");
            return false;
        }

        // Optional: prevent duplicate usernames
        if (userRepo.getUserByUsername(user.getUsername().trim()) != null) {
            System.out.println("Username already exists.");
            return false;
        }

        user.setUsername(user.getUsername().trim());
        user.setPassword(user.getPassword().trim());
        return userRepo.createUser(user);
    }

    // updateUser
    @Override
    public boolean updateUser(User user) {
        if (user == null || user.getId() <= 0) {
            System.out.println("Invalid user data for update.");
            return false;
        }
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            System.out.println("Username cannot be empty.");
            return false;
        }
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            System.out.println("Password cannot be empty.");
            return false;
        }

        user.setUsername(user.getUsername().trim());
        user.setPassword(user.getPassword().trim());
        return userRepo.updateUser(user);
    }

    // deleteUser
    @Override
    public boolean deleteUser(int id) {
        if (id <= 0) {
            System.out.println("Invalid user ID for deletion.");
            return false;
        }
        return userRepo.deleteUser(id);
    }
}