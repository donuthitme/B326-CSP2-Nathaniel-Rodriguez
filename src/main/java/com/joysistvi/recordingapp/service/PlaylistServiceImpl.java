package com.joysistvi.recordingapp.service;

import com.joysistvi.recordingapp.model.Playlist;
import com.joysistvi.recordingapp.repository.PlaylistRepo;

import java.util.List;

public class PlaylistServiceImpl implements PlaylistService {

    private final PlaylistRepo playlistRepo;

    public PlaylistServiceImpl(PlaylistRepo playlistRepo) {
        this.playlistRepo = playlistRepo;
    }

    // getAllPlaylists
    @Override
    public List<Playlist> getAllPlaylists() {
        return playlistRepo.getAllPlaylists();
    }

    // getPlaylistsByUser
    @Override
    public List<Playlist> getPlaylistsByUser(int userId) {
        if (userId <= 0) {
            System.out.println("Invalid User ID");
            return List.of();
        }
        return playlistRepo.getPlaylistsByUser(userId);
    }

    // getPlaylistById
    @Override
    public Playlist getPlaylistById(int id) {
        if (id <= 0) {
            System.out.println("Invalid Playlist ID");
            return null;
        }
        Playlist playlist = playlistRepo.getPlaylistById(id);
        if (playlist == null) {
            System.out.println("Playlist not found");
        }
        return playlist;
    }

    // createPlaylist
    @Override
    public boolean createPlaylist(Playlist playlist) {
        if (playlist == null) {
            System.out.println("Playlist object cannot be null.");
            return false;
        }
        if (playlist.getDateCreated() == null) {
            System.out.println("Date created is required");
            return false;
        }
        if (playlist.getUserId() <= 0) {
            System.out.println("User ID is required");
            return false;
        }
        return playlistRepo.createPlaylist(playlist);
    }

    // updatePlaylist
    @Override
    public boolean updatePlaylist(Playlist playlist) {
        if (playlist == null || playlist.getId() <= 0) {
            System.out.println("Invalid playlist data for update.");
            return false;
        }
        if (playlist.getDateCreated() == null) {
            System.out.println("Date created cannot be empty.");
            return false;
        }
        if (playlist.getUserId() <= 0) {
            System.out.println("User ID is required.");
            return false;
        }
        return playlistRepo.updatePlaylist(playlist);
    }

    // deletePlaylist
    @Override
    public boolean deletePlaylist(int id) {
        if (id <= 0) {
            System.out.println("Invalid playlist ID for deletion.");
            return false;
        }
        return playlistRepo.deletePlaylist(id);
    }
}