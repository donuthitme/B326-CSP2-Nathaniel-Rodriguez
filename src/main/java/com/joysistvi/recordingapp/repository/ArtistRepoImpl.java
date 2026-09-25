package com.joysistvi.recordingapp.repository;

import com.joysistvi.recordingapp.config.DbConnection;
import com.joysistvi.recordingapp.model.Artists;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArtistRepoImpl implements ArtistsRepo {

    private final DbConnection dbConnection;

    public ArtistRepoImpl(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    // Database Access Logic

    //getAllArtists
    @Override
    public List<Artists> getAllArtists() {
        List<Artists> artists = new ArrayList<>();
        String query = "SELECT * FROM artists WHERE is_archived = 0";

        try (Connection conn = dbConnection.connect();
             Statement stmnt = conn.createStatement();
             ResultSet result = stmnt.executeQuery(query)) {

            while (result.next()) {
                artists.add(new Artists(result.getInt("id"), result.getString("name")));
            }

        } catch (SQLException e) {
            System.err.println("Get All Artists: " + e.getMessage());
        }
        return artists;
    }

    //getAllArchivedArtists
    @Override
    public List<Artists> getAllArchivedArtists() {
        List<Artists> artists = new ArrayList<>();
        String query = "SELECT * FROM artists WHERE is_archived = 1";

        try (Connection conn = dbConnection.connect();
             Statement stmnt = conn.createStatement();
             ResultSet result = stmnt.executeQuery(query)) {

            while (result.next()) {
                artists.add(new Artists(result.getInt("id"), result.getString("name")));
            }

        } catch (SQLException e) {
            System.err.println("Get Archived Artists: " + e.getMessage());
        }
        return artists;
    }

    //getArtistsById
    @Override
    public Artists getArtistsById(int id) {
        String query = "SELECT * FROM artists WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, id);

            try (ResultSet res = prep.executeQuery()) {
                if (res.next()) {
                    return new Artists(res.getInt("id"), res.getString("name"));
                }
            }

        } catch (SQLException e) {
            System.err.println("Read Artist By Id Error: " + e.getMessage());
        }
        return null;
    }

    //searchArtists
    @Override
    public List<Artists> searchArtists(String keyword) {
        List<Artists> artists = new ArrayList<>();
        String query = "SELECT * FROM artists WHERE name LIKE ? AND is_archived = 0";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setString(1, "%" + keyword + "%");
            try (ResultSet res = prep.executeQuery()) {
                while (res.next()) {
                    artists.add(new Artists(res.getInt("id"), res.getString("name")
                    ));
                }
            }

        } catch (SQLException e) {
            System.err.println("Search Artist Error: " + e.getMessage());
        }

        return artists;
    }

    //createArtist
    @Override
    public boolean createArtist(Artists artist) {
        String query = "INSERT INTO artists (name) VALUES (?)";

        try (Connection conn = dbConnection.connect()) {
            PreparedStatement prep = conn.prepareStatement(query);

            prep.setString(1, artist.getName());

            int rowsAffected = prep.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Create Artist: " + e.getMessage());
        }
        return false;
    }

    //updateArtist
    @Override
    public boolean updateArtist(Artists artist) {
        String query = "UPDATE artists SET name = ? WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setString(1, artist.getName());
            prep.setInt(2, artist.getId());

            int rowsAffected = prep.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Update Artist: " + e.getMessage());
        }
        return false;
    }

    //archiveArtist
    @Override
    public boolean archiveArtist(int id) {
        String query = "UPDATE artists SET is_archived = 1 WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, id);

            int rowsAffected = prep.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Archive Artist: " + e.getMessage());
        }
        return false;
    }

    //restoreArtist
    @Override
    public boolean restoreArtist(int id) {
        String query = "UPDATE artists SET is_archived = 0 WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, id);

            int rowsAffected = prep.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Restore Artist: " + e.getMessage());
        }
        return false;
    }

    //deleteArtist
    @Override
    public boolean deleteArtist(int id) {
        String query = "DELETE FROM artists WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, id);

            int rowsAffected = prep.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Delete Artist: " + e.getMessage());
        }
        return false;
    }

}
