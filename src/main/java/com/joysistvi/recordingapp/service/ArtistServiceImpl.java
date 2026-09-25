package com.joysistvi.recordingapp.service;

import com.joysistvi.recordingapp.model.Artists;
import com.joysistvi.recordingapp.repository.ArtistsRepo;

import java.util.List;

public class ArtistServiceImpl implements ArtistService {

    private final ArtistsRepo artistsRepo; // Composistion

    // Constructor injection
    public ArtistServiceImpl(ArtistsRepo artistsRepo) {
        this.artistsRepo = artistsRepo;
    }

    @Override
    public List<Artists> readAllArtists() {
        return artistsRepo.readAllArtists();
    }

    @Override
    public List<Artists> readAllArchivedArtists() {
        return artistsRepo.readAllArchivedArtists();
    }

    @Override
    public Artists readArtistsById(int id) {
        if (id < 0) {
            System.out.println("Invalid Artist ID");
            return null;
        }
        Artists artists = artistsRepo.readArtistsById(id);
        if (artists == null) {
            System.out.println("Artists with ID not found");
        }
        return artists;
    }

    @Override
    public List<Artists> searchArtists(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            System.out.println("Search keyword cannot be empty");
            return List.of(); //instead of returning null
        }
        return artistsRepo.searchArtists(keyword.trim());
    }


   @Override
    public boolean createArtist(Artists artists) {
        if (artists.getName() == null || artists.getName().isEmpty()) {
            System.out.println("Artist name is required");
            return false;
        }

        return true;
   }

    @Override
    public boolean updateArtist(Artists artist) {
        if (artist == null || artist.getId() <= 0) {
            System.out.println("Invalid Artist");
            return false;
        }
        if (artist.getName() == null || artist.getName().trim().isEmpty()) {
            System.out.println("Artist name is required");
            return false;
        }
        return artistsRepo.updateArtist(artist);
    }

    @Override
    public boolean archiveArtist(int id) {
        if (id <= 0) return false;
        return artistsRepo.archiveArtist(id);
    }

    @Override
    public boolean restoreArtist(int id) {
        if (id <= 0) return false;
        return artistsRepo.restoreArtist(id);
    }

    @Override
    public boolean deleteArtist(int id) {
        if (id <= 0) return false;
        return artistsRepo.deleteArtist(id);
    }

}
