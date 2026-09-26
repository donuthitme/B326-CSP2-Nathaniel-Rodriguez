package com.joysistvi.recordingapp.controller;

import com.joysistvi.recordingapp.model.Song;
import com.joysistvi.recordingapp.service.SongService;

import java.util.List;

public class SongController {

    private final SongService songService;

    public SongController(SongService songService) {
        this.songService = songService;
    }

    public List<Song> getAllSongs() {
        return songService.getAllSongs();
    }

    public List<Song> getSongsByAlbum(int albumId) {
        return songService.getSongsByAlbum(albumId);
    }

    public Song getSongById(int id) {
        return songService.getSongById(id);
    }

    public List<Song> searchSongs(String keyword) {
        return songService.searchSongs(keyword);
    }

    public boolean createSong(String title, String length, String genre, int albumId) {
        return songService.createSong(new Song(title, length, genre, albumId));
    }

    public boolean updateSong(int id, String title, String length, String genre, int albumId) {
        return songService.updateSong(new Song(id, title, length, genre, albumId));
    }

    public boolean deleteSong(int id) {
        return songService.deleteSong(id);
    }
}