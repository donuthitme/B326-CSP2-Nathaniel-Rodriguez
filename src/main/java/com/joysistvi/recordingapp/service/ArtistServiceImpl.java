package com.joysistvi.recordingapp.service;

import com.joysistvi.recordingapp.model.Artists;
import com.joysistvi.recordingapp.repository.ArtistsRepo;

import java.util.List;

public class ArtistServiceImpl implements ArtistService {

    private final ArtistsRepo artistsRepo; // Composition

    // Constructor injection
    public ArtistServiceImpl(ArtistsRepo artistsRepo) {
        this.artistsRepo = artistsRepo;
    }

    //getAllArtists
    @Override
    public List<Artists> getAllArtists() {
        return artistsRepo.getAllArtists();
    }

    //getAllArchivedArtists
    @Override
    public List<Artists> getAllArchivedArtists() {
        return artistsRepo.getAllArchivedArtists();
    }

    //getArtistsById
    @Override
    public Artists getArtistsById(int id) {
        if (id <= 0) {
            System.out.println("Invalid Artist ID");
            return null;
        }
        Artists artists = artistsRepo.getArtistsById(id);
        if (artists == null) {
            System.out.println("Artists not found");
        }
        return artists;
    }

    //searchArtists
    @Override
    public List<Artists> searchArtists(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            System.out.println("Search keyword cannot be empty.");
            return List.of(); //instead of returning null
        }
        return artistsRepo.searchArtists(keyword.trim());
    }

    //createArtist
    @Override
    public boolean createArtist(Artists artists) {
        if (artists == null) {
            System.out.println("Artist object cannot be null.");
            return false;
        }

        if (artists.getName() == null || artists.getName().trim().isEmpty()) {
            System.out.println("Artist name is required");
            return false;
        }

        artists.setName(artists.getName().trim());
        return artistsRepo.createArtist(artists);
   }

    //updateArtist
    @Override
    public boolean updateArtist(Artists artist) {
        if (artist == null || artist.getId() <= 0) {
            System.out.println("Invalid artist data for update.");
            return false;
        }

        if (artist.getName() == null || artist.getName().trim().isEmpty()) {
            System.out.println("Artist name cannot be empty.");
            return false;
        }

        artist.setName(artist.getName().trim());
        return artistsRepo.updateArtist(artist);
    }

    //archiveArtist
    @Override
    public boolean archiveArtist(int id) {
        if (id <= 0) {
            System.out.println("Invalid artist ID for archive.");
            return false;
        }

        return artistsRepo.archiveArtist(id);
    }

    //restoreArtist
    @Override
    public boolean restoreArtist(int id) {
        if (id <= 0) {
            System.out.println("Invalid artist ID for restore.");
            return false;
        }

        return artistsRepo.restoreArtist(id);
    }

    //deleteArtist
    @Override
    public boolean deleteArtist(int id) {
        if (id <= 0) {
            System.out.println("Invalid artist ID for deletion.");
            return false;
        }

        return artistsRepo.deleteArtist(id);
    }

}
