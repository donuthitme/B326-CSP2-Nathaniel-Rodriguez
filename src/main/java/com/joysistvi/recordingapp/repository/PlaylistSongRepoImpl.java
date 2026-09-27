package com.joysistvi.recordingapp.repository;

import com.joysistvi.recordingapp.config.DbConnection;
import com.joysistvi.recordingapp.model.PlaylistSong;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaylistSongRepoImpl implements PlaylistSongRepo {

    private final DbConnection dbConnection;

    public PlaylistSongRepoImpl(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    // getAllPlaylistSongs
    @Override
    public List<PlaylistSong> getAllPlaylistSongs() {
        List<PlaylistSong> list = new ArrayList<>();
        String query = "SELECT * FROM playlist_songs";

        try (Connection conn = dbConnection.connect();
             Statement stmnt = conn.createStatement();
             ResultSet result = stmnt.executeQuery(query)) {

            while (result.next()) {
                list.add(new PlaylistSong(
                        result.getInt("id"),
                        result.getInt("playlist_id"),
                        result.getInt("song_id")));
            }

        } catch (SQLException e) {
            System.err.println("Get All PlaylistSongs: " + e.getMessage());
        }
        return list;
    }

    // getSongsByPlaylist
    @Override
    public List<PlaylistSong> getSongsByPlaylist(int playlistId) {
        List<PlaylistSong> list = new ArrayList<>();
        String query = "SELECT * FROM playlist_songs WHERE playlist_id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, playlistId);

            try (ResultSet result = prep.executeQuery()) {
                while (result.next()) {
                    list.add(new PlaylistSong(
                            result.getInt("id"),
                            result.getInt("playlist_id"),
                            result.getInt("song_id")));
                }
            }

        } catch (SQLException e) {
            System.err.println("Get Songs By Playlist: " + e.getMessage());
        }
        return list;
    }

    // getPlaylistSongById
    @Override
    public PlaylistSong getPlaylistSongById(int id) {
        String query = "SELECT * FROM playlist_songs WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, id);

            try (ResultSet result = prep.executeQuery()) {
                if (result.next()) {
                    return new PlaylistSong(
                            result.getInt("id"),
                            result.getInt("playlist_id"),
                            result.getInt("song_id"));
                }
            }

        } catch (SQLException e) {
            System.err.println("Get PlaylistSong By Id Error: " + e.getMessage());
        }
        return null;
    }

    // addSongToPlaylist
    @Override
    public boolean addSongToPlaylist(PlaylistSong playlistSong) {
        String query = "INSERT INTO playlist_songs (playlist_id, song_id) VALUES (?, ?)";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, playlistSong.getPlaylistId());
            prep.setInt(2, playlistSong.getSongId());

            return prep.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Add Song To Playlist: " + e.getMessage());
        }
        return false;
    }

    // removeSongFromPlaylist
    @Override
    public boolean removeSongFromPlaylist(int playlistId, int songId) {
        String query = "DELETE FROM playlist_songs WHERE playlist_id = ? AND song_id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, playlistId);
            prep.setInt(2, songId);

            return prep.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Remove Song From Playlist: " + e.getMessage());
        }
        return false;
    }

    // deletePlaylistSong
    @Override
    public boolean deletePlaylistSong(int id) {
        String query = "DELETE FROM playlist_songs WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, id);
            return prep.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Delete PlaylistSong: " + e.getMessage());
        }
        return false;
    }

    // getSongsInPlaylistWithDetails
    @Override
    public List<Object[]> getSongsInPlaylistWithDetails(int playlistId) {
        List<Object[]> rows = new ArrayList<>();
        String query = "SELECT s.id, s.title, s.length, s.genre, " +
                "al.name AS album_name, ar.name AS artist_name " +
                "FROM playlist_songs ps " +
                "JOIN songs s ON ps.song_id = s.id " +
                "JOIN albums al ON s.album_id = al.id " +
                "JOIN artists ar ON al.artist_id = ar.id " +
                "WHERE ps.playlist_id = ? " +
                "ORDER BY s.title";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, playlistId);

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
            System.err.println("Get Songs In Playlist With Details: " + e.getMessage());
        }
        return rows;
    }

    // isSongInPlaylist
    @Override
    public boolean isSongInPlaylist(int playlistId, int songId) {
        String query = "SELECT COUNT(*) AS cnt FROM playlist_songs " +
                "WHERE playlist_id = ? AND song_id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, playlistId);
            prep.setInt(2, songId);

            try (ResultSet result = prep.executeQuery()) {
                if (result.next()) {
                    return result.getInt("cnt") > 0;
                }
            }

        } catch (SQLException e) {
            System.err.println("Is Song In Playlist: " + e.getMessage());
        }
        return false;
    }
}