package com.joysistvi.recordingapp.dao;

import com.joysistvi.recordingapp.config.DbConnection;

import java.sql.*;

public class ArtistsDao {

    //Composition
    private final DbConnection dbConnection;

    //Construct Injection
    public ArtistsDao(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    //CRUD
    public void readAllArtists() {
        String query = "SELECT * FROM artists WHERE is_archived = 0";

        //create statement
        // try-with resources
        try (Connection conn = dbConnection.connect();
             Statement stmnt = conn.createStatement(); // create statement
             ResultSet result = stmnt.executeQuery(query); // execute query
             ) {

            // Top border
            System.out.println("+-------+----------------------+");
            // Header
            System.out.printf("| %-5s | %-20s |%n", "ID", "Name");
            // Divider under header
            System.out.println("+-------+----------------------+");


            //extract data
            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");

                System.out.printf("| %-5d | %-20s |%n", id, name);
            }
            //Bottom border
            System.out.println("+-------+----------------------+");

        } catch (SQLException e) {
            System.err.println("Get All Artists: " + e.getMessage());
        }
    }

    public void readAllArchivedArtists() {
        String query = "SELECT * FROM artists WHERE is_archived = 1";

        //create statement
        // try-with resources
        try (Connection conn = dbConnection.connect();
             Statement stmnt = conn.createStatement(); // create statement
             ResultSet result = stmnt.executeQuery(query); // execute query
        ) {

            // Top border
            System.out.println("+-------+----------------------+");
            // Header
            System.out.printf("| %-5s | %-20s |%n", "ID", "Name");
            // Divider under header
            System.out.println("+-------+----------------------+");


            //extract data
            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");

                System.out.printf("| %-5d | %-20s |%n", id, name);
            }
            //Bottom border
            System.out.println("+-------+----------------------+");

        } catch (SQLException e) {
            System.err.println("Get All Artists: " + e.getMessage());
        }
    }

    public void createArtist(String name) {

        //Validation
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Artist name is required");
            return;
        }

        //parameterized query
        String query = "INSERT INTO artists (name) VALUES (?)";

        try (Connection conn = dbConnection.connect()) {
            PreparedStatement prep = conn.prepareStatement(query);

            prep.setString(1, name); // Set wild card values

            int rows = prep.executeUpdate();

            //synchronization
            System.out.println(rows > 0 ? "Artist " + name + " Added Successfully.\n" : " Failed to add artist.");
            readAllArtists();
        } catch (SQLException e) {
            System.err.println("Create Artist: " + e.getMessage());
        }
    }

    public void updateArtist(int id, String name) {

        if (id <= 0) {
            System.out.println("Invalid Artist ID");
            return;
        }
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Artist name is required");
            return;
        }

        String query = "UPDATE artists SET name = ? WHERE id = ?";

        try {
            Connection conn = dbConnection.connect();
            PreparedStatement prep = conn.prepareStatement(query);

            prep.setString(1, name);
            prep.setInt(2, id);

            int rows = prep.executeUpdate();

            System.out.println(rows > 0 ? "Artist " + name + " Updated Successfully.\n" : " Failed to update artist.");
            readAllArtists();
        } catch (SQLException e) {
            System.out.println("Update Artist: " + e.getMessage());
        }
    }

    public void archiveArtist(int id) {

        String query = "UPDATE artists SET is_archived = 1 WHERE id = ?";

        try (Connection conn = dbConnection.connect()){
            PreparedStatement prep = conn.prepareStatement(query);

            prep.setInt(1, id);

            int rows = prep.executeUpdate();

            System.out.println(rows > 0 ? "Artist " + id + " Archived Successfully.\n" : " Failed to Archived artist.");
            readAllArtists();
        } catch (SQLException e) {
            System.out.println("Archived Artist: " + e.getMessage());
        }
    }

    public void restoreArtist(int id) {

        String query = "UPDATE artists SET is_archived = 0 WHERE id = ?";

        try (Connection conn = dbConnection.connect()){
            PreparedStatement prep = conn.prepareStatement(query);

            prep.setInt(1, id);

            int rows = prep.executeUpdate();

            System.out.println(rows > 0 ? "Artist " + id + " Restored Successfully.\n" : " Failed to Restore artist.");
            readAllArtists();
        } catch (SQLException e) {
            System.out.println("Restore Artist: " + e.getMessage());
        }
    }

    public void deleteArtist(int id) {

        String query = "DELETE FROM  artists WHERE id = ?";

        try (Connection conn = dbConnection.connect()){
            PreparedStatement prep = conn.prepareStatement(query);

            prep.setInt(1, id);

            int rows = prep.executeUpdate();

            System.out.println(rows > 0 ? "Artist " + id + " Deleted Successfully.\n" : " Failed to Delete artist.");
            readAllArtists();
        } catch (SQLException e) {
            System.err.println("Delete Artist: " + e.getMessage());
        }
    }

    public void readArtistsById(int id) {
        String query = "SELECT * FROM artists WHERE id = ?";

        try (Connection conn = dbConnection.connect();
            PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, id);
            ResultSet res = prep.executeQuery();

            System.out.println("+-------+----------------------+");
            System.out.printf("| %-5s | %-20s |%n", "ID", "Name");
            System.out.println("+-------+----------------------+");

            if (res.next()) {
                System.out.printf("| %-5d | %-20s |%n",res.getInt("id"), res.getString("name"));
            }

            System.out.println("+-------+----------------------+");

        } catch (SQLException e) {
            System.err.println("Get ID Artists: " + e.getMessage());
        }
    }

    public void searchArtists(String keyword) {

        if (keyword == null || keyword.trim().isEmpty()) {
            System.out.println("Search keyword should not be empty");
            return;
        }

        String query = "SELECT id, name FROM artists WHERE name LIKE ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setString(1, "%" + keyword.trim() + "%");
            ResultSet res = prep.executeQuery();

            System.out.println("+-------+----------------------+");
            System.out.printf("| %-5s | %-20s |%n", "ID", "Name");
            System.out.println("+-------+----------------------+");

            while (res.next()) {
                System.out.printf("| %-5d | %-20s |%n",res.getInt("id"), res.getString("name"));
            }

            System.out.println("+-------+----------------------+");

        } catch (SQLException e) {
            System.err.println("Get ID Artists: " + e.getMessage());
        }
    }
}
