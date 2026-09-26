package com.joysistvi.recordingapp.service;

import com.joysistvi.recordingapp.model.Album;

import java.util.List;

public interface AlbumService {

    List<Album> getAllAlbums();
    List<Album> getAlbumsByArtist(int artistId);
    Album getAlbumById(int id);
    List<Album> searchAlbums(String keyword);
    boolean createAlbum(Album album);
    boolean updateAlbum(Album album);
    boolean deleteAlbum(int id);

}