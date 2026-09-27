package com.joysistvi.recordingapp.repository;

import com.joysistvi.recordingapp.config.DbConnection;
import com.joysistvi.recordingapp.model.Album;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlbumRepoImpl implements AlbumRepo {

    private final DbConnection dbConnection;

    public AlbumRepoImpl(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    // getAllAlbums
    @Override
    public List<Album> getAllAlbums() {
        List<Album> albums = new ArrayList<>();
        String query = "SELECT * FROM albums";

        try (Connection conn = dbConnection.connect();
             Statement stmnt = conn.createStatement();
             ResultSet result = stmnt.executeQuery(query)) {

            while (result.next()) {
                albums.add(new Album(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getInt("year"),
                        result.getInt("artist_id")));
            }

        } catch (SQLException e) {
            System.err.println("Get All Albums: " + e.getMessage());
        }
        return albums;
    }

    // getAlbumsByArtist
    @Override
    public List<Album> getAlbumsByArtist(int artistId) {
        List<Album> albums = new ArrayList<>();
        String query = "SELECT * FROM albums WHERE artist_id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, artistId);

            try (ResultSet result = prep.executeQuery()) {
                while (result.next()) {
                    albums.add(new Album(
                            result.getInt("id"),
                            result.getString("name"),
                            result.getInt("year"),
                            result.getInt("artist_id")));
                }
            }

        } catch (SQLException e) {
            System.err.println("Get Albums By Artist: " + e.getMessage());
        }
        return albums;
    }

    // getAlbumById
    @Override
    public Album getAlbumById(int id) {
        String query = "SELECT * FROM albums WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, id);

            try (ResultSet result = prep.executeQuery()) {
                if (result.next()) {
                    return new Album(
                            result.getInt("id"),
                            result.getString("name"),
                            result.getInt("year"),
                            result.getInt("artist_id"));
                }
            }

        } catch (SQLException e) {
            System.err.println("Get Album By Id Error: " + e.getMessage());
        }
        return null;
    }

    // searchAlbums
    @Override
    public List<Album> searchAlbums(String keyword) {
        List<Album> albums = new ArrayList<>();
        String query = "SELECT * FROM albums WHERE name LIKE ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setString(1, "%" + keyword + "%");

            try (ResultSet result = prep.executeQuery()) {
                while (result.next()) {
                    albums.add(new Album(
                            result.getInt("id"),
                            result.getString("name"),
                            result.getInt("year"),
                            result.getInt("artist_id")));
                }
            }

        } catch (SQLException e) {
            System.err.println("Search Album Error: " + e.getMessage());
        }
        return albums;
    }

    // createAlbum
    @Override
    public boolean createAlbum(Album album) {
        String query = "INSERT INTO albums (name, year, artist_id) VALUES (?, ?, ?)";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setString(1, album.getName());
            prep.setInt(2, album.getYear());
            prep.setInt(3, album.getArtistId());

            int rowsAffected = prep.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Create Album: " + e.getMessage());
        }
        return false;
    }

    // updateAlbum
    @Override
    public boolean updateAlbum(Album album) {
        String query = "UPDATE albums SET name = ?, year = ?, artist_id = ? WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setString(1, album.getName());
            prep.setInt(2, album.getYear());
            prep.setInt(3, album.getArtistId());
            prep.setInt(4, album.getId());

            int rowsAffected = prep.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Update Album: " + e.getMessage());
        }
        return false;
    }

    // deleteAlbum
    @Override
    public boolean deleteAlbum(int id) {
        String query = "DELETE FROM albums WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, id);

            int rowsAffected = prep.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Delete Album: " + e.getMessage());
        }
        return false;
    }

    // getAllAlbumsWithArtist
    @Override
    public List<Object[]> getAllAlbumsWithArtist() {
        List<Object[]> rows = new ArrayList<>();
        String query = "SELECT a.id, a.name, a.year, ar.name AS artist_name " +
                "FROM albums a " +
                "JOIN artists ar ON a.artist_id = ar.id " +
                "ORDER BY a.id";

        try (Connection conn = dbConnection.connect();
             Statement stmnt = conn.createStatement();
             ResultSet result = stmnt.executeQuery(query)) {

            while (result.next()) {
                rows.add(new Object[]{
                        result.getInt("id"),
                        result.getString("name"),
                        result.getInt("year"),
                        result.getString("artist_name")
                });
            }

        } catch (SQLException e) {
            System.err.println("Get All Albums With Artist: " + e.getMessage());
        }
        return rows;
    }
}