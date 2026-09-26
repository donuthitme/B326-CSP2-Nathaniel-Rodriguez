package com.joysistvi.recordingapp.controller;

import com.joysistvi.recordingapp.model.Album;
import com.joysistvi.recordingapp.service.AlbumService;

import java.util.List;

public class AlbumController {

    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    public List<Album> getAllAlbums() {
        return albumService.getAllAlbums();
    }

    public List<Album> getAlbumsByArtist(int artistId) {
        return albumService.getAlbumsByArtist(artistId);
    }

    public Album getAlbumById(int id) {
        return albumService.getAlbumById(id);
    }

    public List<Album> searchAlbums(String keyword) {
        return albumService.searchAlbums(keyword);
    }

    public boolean createAlbum(String name, int year, int artistId) {
        return albumService.createAlbum(new Album(name, year, artistId));
    }

    public boolean updateAlbum(int id, String name, int year, int artistId) {
        return albumService.updateAlbum(new Album(id, name, year, artistId));
    }

    public boolean deleteAlbum(int id) {
        return albumService.deleteAlbum(id);
    }
}