package com.joysistvi.recordingapp.repository;

import com.joysistvi.recordingapp.config.DbConnection;
import com.joysistvi.recordingapp.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepoImpl implements UserRepo {

    private final DbConnection dbConnection;

    public UserRepoImpl(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    // getAllUsers
    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";

        try (Connection conn = dbConnection.connect();
             Statement stmnt = conn.createStatement();
             ResultSet result = stmnt.executeQuery(query)) {

            while (result.next()) {
                users.add(new User(
                        result.getInt("id"),
                        result.getString("username"),
                        result.getString("password"),
                        result.getString("role")));     // ← NEW
            }

        } catch (SQLException e) {
            System.err.println("Get All Users: " + e.getMessage());
        }
        return users;
    }

    // getUserById
    @Override
    public User getUserById(int id) {
        String query = "SELECT * FROM users WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, id);

            try (ResultSet result = prep.executeQuery()) {
                if (result.next()) {
                    return new User(
                            result.getInt("id"),
                            result.getString("username"),
                            result.getString("password"),
                            result.getString("role"));  // ← NEW
                }
            }

        } catch (SQLException e) {
            System.err.println("Get User By Id Error: " + e.getMessage());
        }
        return null;
    }

    // getUserByUsername
    @Override
    public User getUserByUsername(String username) {
        String query = "SELECT * FROM users WHERE username = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setString(1, username);

            try (ResultSet result = prep.executeQuery()) {
                if (result.next()) {
                    return new User(
                            result.getInt("id"),
                            result.getString("username"),
                            result.getString("password"),
                            result.getString("role"));  // ← NEW
                }
            }

        } catch (SQLException e) {
            System.err.println("Get User By Username Error: " + e.getMessage());
        }
        return null;
    }

    // createUser
    @Override
    public boolean createUser(User user) {
        String query = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setString(1, user.getUsername());
            prep.setString(2, user.getPassword());
            prep.setString(3, user.getRole());          // ← NEW

            return prep.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Create User: " + e.getMessage());
        }
        return false;
    }

    // updateUser
    @Override
    public boolean updateUser(User user) {
        String query = "UPDATE users SET username = ?, password = ?, role = ? WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setString(1, user.getUsername());
            prep.setString(2, user.getPassword());
            prep.setString(3, user.getRole());          // ← NEW
            prep.setInt(4, user.getId());

            return prep.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Update User: " + e.getMessage());
        }
        return false;
    }

    // deleteUser
    @Override
    public boolean deleteUser(int id) {
        String query = "DELETE FROM users WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, id);
            return prep.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Delete User: " + e.getMessage());
        }
        return false;
    }

}