package com.joysistvi.recordingapp.service;

import com.joysistvi.recordingapp.model.Playlist;

import java.util.List;

public interface PlaylistService {

    List<Playlist> getAllPlaylists();
    List<Playlist> getPlaylistsByUser(int userId);
    Playlist getPlaylistById(int id);
    boolean createPlaylist(Playlist playlist);
    boolean updatePlaylist(Playlist playlist);
    boolean deletePlaylist(int id);

}