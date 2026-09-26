package com.joysistvi.recordingapp.service;

import com.joysistvi.recordingapp.model.Album;
import com.joysistvi.recordingapp.repository.AlbumRepo;

import java.util.List;

public class AlbumServiceImpl implements AlbumService {

    private final AlbumRepo albumRepo; // Composition

    // Constructor injection
    public AlbumServiceImpl(AlbumRepo albumRepo) {
        this.albumRepo = albumRepo;
    }

    // getAllAlbums
    @Override
    public List<Album> getAllAlbums() {
        return albumRepo.getAllAlbums();
    }

    // getAlbumsByArtist
    @Override
    public List<Album> getAlbumsByArtist(int artistId) {
        if (artistId <= 0) {
            System.out.println("Invalid Artist ID");
            return List.of();
        }
        return albumRepo.getAlbumsByArtist(artistId);
    }

    // getAlbumById
    @Override
    public Album getAlbumById(int id) {
        if (id <= 0) {
            System.out.println("Invalid Album ID");
            return null;
        }
        Album album = albumRepo.getAlbumById(id);
        if (album == null) {
            System.out.println("Album not found");
        }
        return album;
    }

    // searchAlbums
    @Override
    public List<Album> searchAlbums(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            System.out.println("Search keyword cannot be empty.");
            return List.of();
        }
        return albumRepo.searchAlbums(keyword.trim());
    }

    // createAlbum
    @Override
    public boolean createAlbum(Album album) {
        if (album == null) {
            System.out.println("Album object cannot be null.");
            return false;
        }

        if (album.getName() == null || album.getName().trim().isEmpty()) {
            System.out.println("Album name is required");
            return false;
        }

        if (album.getYear() <= 0) {
            System.out.println("Album year is invalid");
            return false;
        }

        if (album.getArtistId() <= 0) {
            System.out.println("Artist ID is required");
            return false;
        }

        album.setName(album.getName().trim());
        return albumRepo.createAlbum(album);
    }

    // updateAlbum
    @Override
    public boolean updateAlbum(Album album) {
        if (album == null || album.getId() <= 0) {
            System.out.println("Invalid album data for update.");
            return false;
        }

        if (album.getName() == null || album.getName().trim().isEmpty()) {
            System.out.println("Album name cannot be empty.");
            return false;
        }

        if (album.getYear() <= 0) {
            System.out.println("Album year is invalid.");
            return false;
        }

        if (album.getArtistId() <= 0) {
            System.out.println("Artist ID is required.");
            return false;
        }

        album.setName(album.getName().trim());
        return albumRepo.updateAlbum(album);
    }

    // deleteAlbum
    @Override
    public boolean deleteAlbum(int id) {
        if (id <= 0) {
            System.out.println("Invalid album ID for deletion.");
            return false;
        }

        return albumRepo.deleteAlbum(id);
    }
}