package com.joysistvi.recordingapp.config;

import com.joysistvi.recordingapp.cliview.AlbumView;
import com.joysistvi.recordingapp.cliview.ArtistView;
import com.joysistvi.recordingapp.cliview.MainView;
import com.joysistvi.recordingapp.controller.AlbumController;
import com.joysistvi.recordingapp.controller.ArtistsController;
import com.joysistvi.recordingapp.dao.ArtistsDao;
import com.joysistvi.recordingapp.repository.AlbumRepo;
import com.joysistvi.recordingapp.repository.AlbumRepoImpl;
import com.joysistvi.recordingapp.repository.ArtistRepoImpl;
import com.joysistvi.recordingapp.repository.ArtistsRepo;
import com.joysistvi.recordingapp.service.AlbumService;
import com.joysistvi.recordingapp.service.AlbumServiceImpl;
import com.joysistvi.recordingapp.service.ArtistService;
import com.joysistvi.recordingapp.service.ArtistServiceImpl;


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

        //Main Menu
        MainView mainView = new MainView(artistView, albumView);
        mainView.showMainMenu();


    }
}
