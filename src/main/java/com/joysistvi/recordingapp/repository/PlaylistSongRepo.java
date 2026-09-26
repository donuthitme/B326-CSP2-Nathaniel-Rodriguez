package com.joysistvi.recordingapp.repository;

import com.joysistvi.recordingapp.model.PlaylistSong;

import java.util.List;

public interface PlaylistSongRepo {

    List<PlaylistSong> getAllPlaylistSongs();
    List<PlaylistSong> getSongsByPlaylist(int playlistId);
    PlaylistSong getPlaylistSongById(int id);
    boolean addSongToPlaylist(PlaylistSong playlistSong);
    boolean removeSongFromPlaylist(int playlistId, int songId);
    boolean deletePlaylistSong(int id);

}