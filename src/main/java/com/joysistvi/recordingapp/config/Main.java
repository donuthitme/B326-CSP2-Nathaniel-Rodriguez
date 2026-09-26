package com.joysistvi.recordingapp.config;

import com.joysistvi.recordingapp.cliview.MainView;
import com.joysistvi.recordingapp.dao.ArtistsDao;
import com.joysistvi.recordingapp.repository.*;
import com.joysistvi.recordingapp.service.*;
import com.joysistvi.recordingapp.controller.*;
import com.joysistvi.recordingapp.cliview.*;


import java.sql.Connection;
import java.sql.SQLException;

public class Main extends DbConnection {
    public static void main(String[] args) {
        DbConnection dbConnection = new DbConnection();
        ArtistsDao artistDao = new ArtistsDao(dbConnection);

        //Artists
        ArtistsRepo artistRepo = new ArtistRepoImpl(dbConnection);
        ArtistService artistService = new ArtistServiceImpl(artistRepo);
        ArtistsController artistController = new ArtistsController(artistService);
        ArtistView artistView = new ArtistView(artistController);

        //Album
        AlbumRepo albumRepo = new AlbumRepoImpl(dbConnection);
        AlbumService albumService = new AlbumServiceImpl(albumRepo);
        AlbumController albumController = new AlbumController(albumService);
        AlbumView albumView = new AlbumView(albumController);

        //Song
        SongRepo songRepo = new SongRepoImpl(dbConnection);
        SongService songService = new SongServiceImpl(songRepo);
        SongController songController = new SongController(songService);
        SongView songView = new SongView(songController);

        //Playlist
        PlaylistRepo playlistRepo = new PlaylistRepoImpl(dbConnection);
        PlaylistService playlistService = new PlaylistServiceImpl(playlistRepo);
        PlaylistController playlistController = new PlaylistController(playlistService);
        PlaylistView playlistView = new PlaylistView(playlistController);

        //Main Menu
        MainView mainView = new MainView(artistView, albumView, songView,  playlistView);
        mainView.showMainMenu();


    }
}
