package com.joysistvi.recordingapp.service;

import com.joysistvi.recordingapp.model.Song;
import com.joysistvi.recordingapp.repository.SongRepo;

import java.util.List;

public class SongServiceImpl implements SongService {

    private final SongRepo songRepo;

    public SongServiceImpl(SongRepo songRepo) {
        this.songRepo = songRepo;
    }

    // getAllSongs
    @Override
    public List<Song> getAllSongs() {
        return songRepo.getAllSongs();
    }

    // getSongsByAlbum
    @Override
    public List<Song> getSongsByAlbum(int albumId) {
        if (albumId <= 0) {
            System.out.println("Invalid Album ID");
            return List.of();
        }
        return songRepo.getSongsByAlbum(albumId);
    }

    // getSongById
    @Override
    public Song getSongById(int id) {
        if (id <= 0) {
            System.out.println("Invalid Song ID");
            return null;
        }
        Song song = songRepo.getSongById(id);
        if (song == null) {
            System.out.println("Song not found");
        }
        return song;
    }

    // searchSongs
    @Override
    public List<Song> searchSongs(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            System.out.println("Search keyword cannot be empty.");
            return List.of();
        }
        return songRepo.searchSongs(keyword.trim());
    }

    // createSong
    @Override
    public boolean createSong(Song song) {
        if (song == null) {
            System.out.println("Song object cannot be null.");
            return false;
        }
        if (song.getTitle() == null || song.getTitle().trim().isEmpty()) {
            System.out.println("Song title is required");
            return false;
        }
        if (song.getLength() == null || song.getLength().trim().isEmpty()) {
            System.out.println("Song length is required");
            return false;
        }
        if (song.getGenre() == null || song.getGenre().trim().isEmpty()) {
            System.out.println("Song genre is required");
            return false;
        }
        if (song.getAlbumId() <= 0) {
            System.out.println("Album ID is required");
            return false;
        }

        song.setTitle(song.getTitle().trim());
        song.setLength(song.getLength().trim());
        song.setGenre(song.getGenre().trim());
        return songRepo.createSong(song);
    }

    // updateSong
    @Override
    public boolean updateSong(Song song) {
        if (song == null || song.getId() <= 0) {
            System.out.println("Invalid song data for update.");
            return false;
        }
        if (song.getTitle() == null || song.getTitle().trim().isEmpty()) {
            System.out.println("Song title cannot be empty.");
            return false;
        }
        if (song.getLength() == null || song.getLength().trim().isEmpty()) {
            System.out.println("Song length cannot be empty.");
            return false;
        }
        if (song.getGenre() == null || song.getGenre().trim().isEmpty()) {
            System.out.println("Song genre cannot be empty.");
            return false;
        }
        if (song.getAlbumId() <= 0) {
            System.out.println("Album ID is required.");
            return false;
        }

        song.setTitle(song.getTitle().trim());
        song.setLength(song.getLength().trim());
        song.setGenre(song.getGenre().trim());
        return songRepo.updateSong(song);
    }

    // deleteSong
    @Override
    public boolean deleteSong(int id) {
        if (id <= 0) {
            System.out.println("Invalid song ID for deletion.");
            return false;
        }
        return songRepo.deleteSong(id);
    }
}