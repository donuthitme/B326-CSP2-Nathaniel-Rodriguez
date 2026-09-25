package com.joysistvi.recordingapp.service;

import com.joysistvi.recordingapp.model.Artists;

import java.util.List;

public interface ArtistService {
    List<Artists> getAllArtists();
    Artists getArtistsById(int id);
    List<Artists> searchArtists(String keyword);
    boolean createArtist(Artists artists);
    boolean updateArtist(Artists artists);
    boolean archiveArtist(int id);
    boolean restoreArtist(int id);
    boolean deleteArtist(int id);
    List<Artists> getAllArchivedArtists();

}
