package com.joysistvi.recordingapp.service;

import com.joysistvi.recordingapp.model.User;
import com.joysistvi.recordingapp.repository.UserRepo;

import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.getAllUsers();
    }

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

    @Override
    public User getUserByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            System.out.println("Username cannot be empty.");
            return null;
        }
        return userRepo.getUserByUsername(username.trim());
    }

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
        if (userRepo.getUserByUsername(user.getUsername().trim()) != null) {
            System.out.println("Username already exists.");
            return false;
        }
        if (user.getRole() == null || user.getRole().trim().isEmpty()) {
            user.setRole("USER");
        }

        user.setUsername(user.getUsername().trim());
        user.setPassword(user.getPassword().trim());
        return userRepo.createUser(user);
    }

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
        if (user.getRole() == null || user.getRole().trim().isEmpty()) {
            user.setRole("USER");
        }

        user.setUsername(user.getUsername().trim());
        user.setPassword(user.getPassword().trim());
        return userRepo.updateUser(user);
    }

    @Override
    public boolean deleteUser(int id) {
        if (id <= 0) {
            System.out.println("Invalid user ID for deletion.");
            return false;
        }
        return userRepo.deleteUser(id);
    }

    @Override
    public User login(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            System.out.println("Username cannot be empty.");
            return null;
        }
        if (password == null || password.isEmpty()) {
            System.out.println("Password cannot be empty.");
            return null;
        }

        User user = userRepo.getUserByUsername(username.trim());
        if (user == null) {
            System.out.println("Invalid username or password.");
            return null;
        }

        if (!user.getPassword().equals(password)) {
            System.out.println("Invalid username or password.");
            return null;
        }
        return user;
    }

    @Override
    public boolean register(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            System.out.println("Username cannot be empty.");
            return false;
        }
        if (password == null || password.length() < 4) {
            System.out.println("Password must be at least 4 characters.");
            return false;
        }
        if (userRepo.getUserByUsername(username.trim()) != null) {
            System.out.println("Username already taken.");
            return false;
        }

        User newUser = new User(username.trim(), password, "USER");
        return userRepo.createUser(newUser);
    }
}