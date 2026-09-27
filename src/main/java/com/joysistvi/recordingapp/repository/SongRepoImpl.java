package com.joysistvi.recordingapp.repository;

import com.joysistvi.recordingapp.config.DbConnection;
import com.joysistvi.recordingapp.model.Song;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SongRepoImpl implements SongRepo {

    private final DbConnection dbConnection;

    public SongRepoImpl(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    // getAllSongs
    @Override
    public List<Song> getAllSongs() {
        List<Song> songs = new ArrayList<>();
        String query = "SELECT * FROM songs";

        try (Connection conn = dbConnection.connect();
             Statement stmnt = conn.createStatement();
             ResultSet result = stmnt.executeQuery(query)) {

            while (result.next()) {
                songs.add(new Song(
                        result.getInt("id"),
                        result.getString("title"),
                        result.getString("length"),
                        result.getString("genre"),
                        result.getInt("album_id")));
            }

        } catch (SQLException e) {
            System.err.println("Get All Songs: " + e.getMessage());
        }
        return songs;
    }

    // getSongsByAlbum
    @Override
    public List<Song> getSongsByAlbum(int albumId) {
        List<Song> songs = new ArrayList<>();
        String query = "SELECT * FROM songs WHERE album_id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, albumId);

            try (ResultSet result = prep.executeQuery()) {
                while (result.next()) {
                    songs.add(new Song(
                            result.getInt("id"),
                            result.getString("title"),
                            result.getString("length"),
                            result.getString("genre"),
                            result.getInt("album_id")));
                }
            }

        } catch (SQLException e) {
            System.err.println("Get Songs By Album: " + e.getMessage());
        }
        return songs;
    }

    // getSongById
    @Override
    public Song getSongById(int id) {
        String query = "SELECT * FROM songs WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, id);

            try (ResultSet result = prep.executeQuery()) {
                if (result.next()) {
                    return new Song(
                            result.getInt("id"),
                            result.getString("title"),
                            result.getString("length"),
                            result.getString("genre"),
                            result.getInt("album_id"));
                }
            }

        } catch (SQLException e) {
            System.err.println("Get Song By Id Error: " + e.getMessage());
        }
        return null;
    }

    // searchSongs
    @Override
    public List<Song> searchSongs(String keyword) {
        List<Song> songs = new ArrayList<>();
        String query = "SELECT * FROM songs WHERE title LIKE ? OR genre LIKE ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            String pattern = "%" + keyword + "%";
            prep.setString(1, pattern);
            prep.setString(2, pattern);

            try (ResultSet result = prep.executeQuery()) {
                while (result.next()) {
                    songs.add(new Song(
                            result.getInt("id"),
                            result.getString("title"),
                            result.getString("length"),
                            result.getString("genre"),
                            result.getInt("album_id")));
                }
            }

        } catch (SQLException e) {
            System.err.println("Search Song Error: " + e.getMessage());
        }
        return songs;
    }

    // createSong
    @Override
    public boolean createSong(Song song) {
        String query = "INSERT INTO songs (title, length, genre, album_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setString(1, song.getTitle());
            prep.setString(2, song.getLength());
            prep.setString(3, song.getGenre());
            prep.setInt(4, song.getAlbumId());

            return prep.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Create Song: " + e.getMessage());
        }
        return false;
    }

    // updateSong
    @Override
    public boolean updateSong(Song song) {
        String query = "UPDATE songs SET title = ?, length = ?, genre = ?, album_id = ? WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setString(1, song.getTitle());
            prep.setString(2, song.getLength());
            prep.setString(3, song.getGenre());
            prep.setInt(4, song.getAlbumId());
            prep.setInt(5, song.getId());

            return prep.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Update Song: " + e.getMessage());
        }
        return false;
    }

    // deleteSong
    @Override
    public boolean deleteSong(int id) {
        String query = "DELETE FROM songs WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, id);
            return prep.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Delete Song: " + e.getMessage());
        }
        return false;
    }

    // getAllSongsWithDetails
    @Override
    public List<Object[]> getAllSongsWithDetails() {
        List<Object[]> rows = new ArrayList<>();
        String query = "SELECT s.id, s.title, s.length, s.genre, " +
                "al.name AS album_name, ar.name AS artist_name " +
                "FROM songs s " +
                "JOIN albums al ON s.album_id = al.id " +
                "JOIN artists ar ON al.artist_id = ar.id " +
                "ORDER BY s.id";

        try (Connection conn = dbConnection.connect();
             Statement stmnt = conn.createStatement();
             ResultSet result = stmnt.executeQuery(query)) {

            while (result.next()) {
                rows.add(new Object[]{
                        result.getInt("id"),
                        result.getString("title"),
                        result.getString("length"),
                        result.getString("genre"),
                        result.getString("album_name"),
                        result.getString("artist_name")
                });
            }

        } catch (SQLException e) {
            System.err.println("Get All Songs With Details: " + e.getMessage());
        }
        return rows;
    }

    // searchSongsWithDetails
    @Override
    public List<Object[]> searchSongsWithDetails(String keyword) {
        List<Object[]> rows = new ArrayList<>();
        String query = "SELECT s.id, s.title, s.length, s.genre, " +
                "al.name AS album_name, ar.name AS artist_name " +
                "FROM songs s " +
                "JOIN albums al ON s.album_id = al.id " +
                "JOIN artists ar ON al.artist_id = ar.id " +
                "WHERE s.title LIKE ? OR s.genre LIKE ? " +
                "ORDER BY s.id";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            String pattern = "%" + keyword + "%";
            prep.setString(1, pattern);
            prep.setString(2, pattern);

            try (ResultSet result = prep.executeQuery()) {
                while (result.next()) {
                    rows.add(new Object[]{
                            result.getInt("id"),
                            result.getString("title"),
                            result.getString("length"),
                            result.getString("genre"),
                            result.getString("album_name"),
                            result.getString("artist_name")
                    });
                }
            }

        } catch (SQLException e) {
            System.err.println("Search Songs With Details: " + e.getMessage());
        }
        return rows;
    }
}