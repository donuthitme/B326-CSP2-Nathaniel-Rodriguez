package com.joysistvi.recordingapp.service;

import com.joysistvi.recordingapp.model.PlaylistSong;

import java.util.List;

public interface PlaylistSongService {

    List<PlaylistSong> getAllPlaylistSongs();
    List<PlaylistSong> getSongsByPlaylist(int playlistId);
    PlaylistSong getPlaylistSongById(int id);
    boolean addSongToPlaylist(PlaylistSong playlistSong);
    boolean removeSongFromPlaylist(int playlistId, int songId);
    boolean deletePlaylistSong(int id);
    List<Object[]> getSongsInPlaylistWithDetails(int playlistId);
    boolean isSongInPlaylist(int playlistId, int songId);
}