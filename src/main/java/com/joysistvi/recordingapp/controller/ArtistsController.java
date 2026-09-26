package com.joysistvi.recordingapp.controller;

import com.joysistvi.recordingapp.model.Artists;
import com.joysistvi.recordingapp.service.ArtistService;

import java.util.List;

public class ArtistsController {

    private final ArtistService artistService;

    public ArtistsController(ArtistService artistService) {
        this.artistService = artistService;
    }

    public List<Artists> getAllArtists() {
        return artistService.getAllArtists();
    }

    public List<Artists> getAllArchivedArtists() {
        return artistService.getAllArchivedArtists();
    }

    public Artists getArtistsById(int id) {
        return artistService.getArtistsById(id);
    }

    public List<Artists> searchArtists(String keyword) {
        return artistService.searchArtists(keyword);
    }

    public boolean createArtist(String name) {
        return artistService.createArtist(new Artists(name));
    }

    public boolean updateArtist(int id, String name) {
        return artistService.updateArtist(new Artists(id, name));
    }

    public boolean archiveArtist(int id) {
        return artistService.archiveArtist(id);
    }

    public boolean restoreArtist(int id) {
        return artistService.restoreArtist(id);
    }

    public boolean deleteArtist(int id) {
        return artistService.deleteArtist(id);
    }
}