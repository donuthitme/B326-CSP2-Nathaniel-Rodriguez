package com.joysistvi.recordingapp.service;

import com.joysistvi.recordingapp.model.PlaylistSong;
import com.joysistvi.recordingapp.repository.PlaylistSongRepo;

import java.util.List;

public class PlaylistSongServiceImpl implements PlaylistSongService {

    private final PlaylistSongRepo playlistSongRepo;

    public PlaylistSongServiceImpl(PlaylistSongRepo playlistSongRepo) {
        this.playlistSongRepo = playlistSongRepo;
    }

    // getAllPlaylistSongs
    @Override
    public List<PlaylistSong> getAllPlaylistSongs() {
        return playlistSongRepo.getAllPlaylistSongs();
    }

    // getSongsByPlaylist
    @Override
    public List<PlaylistSong> getSongsByPlaylist(int playlistId) {
        if (playlistId <= 0) {
            System.out.println("Invalid Playlist ID");
            return List.of();
        }
        return playlistSongRepo.getSongsByPlaylist(playlistId);
    }

    // getPlaylistSongById
    @Override
    public PlaylistSong getPlaylistSongById(int id) {
        if (id <= 0) {
            System.out.println("Invalid PlaylistSong ID");
            return null;
        }
        PlaylistSong ps = playlistSongRepo.getPlaylistSongById(id);
        if (ps == null) {
            System.out.println("PlaylistSong not found");
        }
        return ps;
    }

    // addSongToPlaylist
    @Override
    public boolean addSongToPlaylist(PlaylistSong playlistSong) {
        if (playlistSong == null) {
            System.out.println("PlaylistSong object cannot be null.");
            return false;
        }
        if (playlistSong.getPlaylistId() <= 0) {
            System.out.println("Playlist ID is required");
            return false;
        }
        if (playlistSong.getSongId() <= 0) {
            System.out.println("Song ID is required");
            return false;
        }
        return playlistSongRepo.addSongToPlaylist(playlistSong);
    }

    // removeSongFromPlaylist
    @Override
    public boolean removeSongFromPlaylist(int playlistId, int songId) {
        if (playlistId <= 0 || songId <= 0) {
            System.out.println("Invalid Playlist ID or Song ID");
            return false;
        }
        return playlistSongRepo.removeSongFromPlaylist(playlistId, songId);
    }

    // deletePlaylistSong
    @Override
    public boolean deletePlaylistSong(int id) {
        if (id <= 0) {
            System.out.println("Invalid PlaylistSong ID for deletion.");
            return false;
        }
        return playlistSongRepo.deletePlaylistSong(id);
    }

    @Override
    public List<Object[]> getSongsInPlaylistWithDetails(int playlistId) {
        if (playlistId <= 0) {
            return List.of();
        }
        return playlistSongRepo.getSongsInPlaylistWithDetails(playlistId);
    }

    @Override
    public boolean isSongInPlaylist(int playlistId, int songId) {
        if (playlistId <= 0 || songId <= 0) {
            return false;
        }
        return playlistSongRepo.isSongInPlaylist(playlistId, songId);
    }
}