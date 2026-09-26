package com.joysistvi.recordingapp.repository;

import com.joysistvi.recordingapp.model.Playlist;

import java.util.List;

public interface PlaylistRepo {

    List<Playlist> getAllPlaylists();
    List<Playlist> getPlaylistsByUser(int userId);
    Playlist getPlaylistById(int id);
    boolean createPlaylist(Playlist playlist);
    boolean updatePlaylist(Playlist playlist);
    boolean deletePlaylist(int id);

}