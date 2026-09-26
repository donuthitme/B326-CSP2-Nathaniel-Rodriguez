package com.joysistvi.recordingapp.controller;

import com.joysistvi.recordingapp.model.Playlist;
import com.joysistvi.recordingapp.service.PlaylistService;

import java.sql.Date;
import java.util.List;

public class PlaylistController {

    private final PlaylistService playlistService;

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    public List<Playlist> getAllPlaylists() {
        return playlistService.getAllPlaylists();
    }

    public List<Playlist> getPlaylistsByUser(int userId) {
        return playlistService.getPlaylistsByUser(userId);
    }

    public Playlist getPlaylistById(int id) {
        return playlistService.getPlaylistById(id);
    }

    public boolean createPlaylist(String dateCreated, int userId) {
        Date sqlDate;
        try {
            sqlDate = Date.valueOf(dateCreated);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid date format. Use yyyy-mm-dd.");
            return false;
        }
        return playlistService.createPlaylist(new Playlist(sqlDate, userId));
    }

    public boolean updatePlaylist(int id, String dateCreated, int userId) {
        Date sqlDate;
        try {
            sqlDate = Date.valueOf(dateCreated);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid date format. Use yyyy-mm-dd.");
            return false;
        }
        return playlistService.updatePlaylist(new Playlist(id, sqlDate, userId));
    }

    public boolean deletePlaylist(int id) {
        return playlistService.deletePlaylist(id);
    }
}