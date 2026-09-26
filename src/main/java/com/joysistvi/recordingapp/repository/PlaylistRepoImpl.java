package com.joysistvi.recordingapp.repository;

import com.joysistvi.recordingapp.config.DbConnection;
import com.joysistvi.recordingapp.model.Playlist;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaylistRepoImpl implements PlaylistRepo {

    private final DbConnection dbConnection;

    public PlaylistRepoImpl(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    // getAllPlaylists
    @Override
    public List<Playlist> getAllPlaylists() {
        List<Playlist> playlists = new ArrayList<>();
        String query = "SELECT * FROM playlists";

        try (Connection conn = dbConnection.connect();
             Statement stmnt = conn.createStatement();
             ResultSet result = stmnt.executeQuery(query)) {

            while (result.next()) {
                playlists.add(new Playlist(
                        result.getInt("id"),
                        result.getDate("date_created"),
                        result.getInt("user_id")));
            }

        } catch (SQLException e) {
            System.err.println("Get All Playlists: " + e.getMessage());
        }
        return playlists;
    }

    // getPlaylistsByUser
    @Override
    public List<Playlist> getPlaylistsByUser(int userId) {
        List<Playlist> playlists = new ArrayList<>();
        String query = "SELECT * FROM playlists WHERE user_id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, userId);

            try (ResultSet result = prep.executeQuery()) {
                while (result.next()) {
                    playlists.add(new Playlist(
                            result.getInt("id"),
                            result.getDate("date_created"),
                            result.getInt("user_id")));
                }
            }

        } catch (SQLException e) {
            System.err.println("Get Playlists By User: " + e.getMessage());
        }
        return playlists;
    }

    // getPlaylistById
    @Override
    public Playlist getPlaylistById(int id) {
        String query = "SELECT * FROM playlists WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, id);

            try (ResultSet result = prep.executeQuery()) {
                if (result.next()) {
                    return new Playlist(
                            result.getInt("id"),
                            result.getDate("date_created"),
                            result.getInt("user_id"));
                }
            }

        } catch (SQLException e) {
            System.err.println("Get Playlist By Id Error: " + e.getMessage());
        }
        return null;
    }

    // createPlaylist
    @Override
    public boolean createPlaylist(Playlist playlist) {
        String query = "INSERT INTO playlists (date_created, user_id) VALUES (?, ?)";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setDate(1, playlist.getDateCreated());
            prep.setInt(2, playlist.getUserId());

            return prep.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Create Playlist: " + e.getMessage());
        }
        return false;
    }

    // updatePlaylist
    @Override
    public boolean updatePlaylist(Playlist playlist) {
        String query = "UPDATE playlists SET date_created = ?, user_id = ? WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setDate(1, playlist.getDateCreated());
            prep.setInt(2, playlist.getUserId());
            prep.setInt(3, playlist.getId());

            return prep.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Update Playlist: " + e.getMessage());
        }
        return false;
    }

    // deletePlaylist
    @Override
    public boolean deletePlaylist(int id) {
        String query = "DELETE FROM playlists WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, id);
            return prep.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Delete Playlist: " + e.getMessage());
        }
        return false;
    }
}