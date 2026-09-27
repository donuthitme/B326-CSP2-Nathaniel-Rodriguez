package com.joysistvi.recordingapp.controller;

import com.joysistvi.recordingapp.model.PlaylistSong;
import com.joysistvi.recordingapp.service.PlaylistSongService;

import java.util.List;

public class PlaylistSongController {

    private final PlaylistSongService playlistSongService;

    public PlaylistSongController(PlaylistSongService playlistSongService) {
        this.playlistSongService = playlistSongService;
    }

    public List<PlaylistSong> getAllPlaylistSongs() {
        return playlistSongService.getAllPlaylistSongs();
    }

    public List<PlaylistSong> getSongsByPlaylist(int playlistId) {
        return playlistSongService.getSongsByPlaylist(playlistId);
    }

    public PlaylistSong getPlaylistSongById(int id) {
        return playlistSongService.getPlaylistSongById(id);
    }

    public boolean addSongToPlaylist(int playlistId, int songId) {
        return playlistSongService.addSongToPlaylist(new PlaylistSong(playlistId, songId));
    }

    public boolean removeSongFromPlaylist(int playlistId, int songId) {
        return playlistSongService.removeSongFromPlaylist(playlistId, songId);
    }

    public boolean deletePlaylistSong(int id) {
        return playlistSongService.deletePlaylistSong(id);
    }

    public List<Object[]> getSongsInPlaylistWithDetails(int playlistId) {
        return playlistSongService.getSongsInPlaylistWithDetails(playlistId);
    }

    public boolean isSongInPlaylist(int playlistId, int songId) {
        return playlistSongService.isSongInPlaylist(playlistId, songId);
    }
}