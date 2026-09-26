package com.joysistvi.recordingapp.repository;

import com.joysistvi.recordingapp.model.Song;

import java.util.List;

public interface SongRepo {

    List<Song> getAllSongs();
    List<Song> getSongsByAlbum(int albumId);
    Song getSongById(int id);
    List<Song> searchSongs(String keyword);
    boolean createSong(Song song);
    boolean updateSong(Song song);
    boolean deleteSong(int id);

}