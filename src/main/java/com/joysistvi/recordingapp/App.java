package com.joysistvi.recordingapp;

import com.joysistvi.recordingapp.cliview.*;
import com.joysistvi.recordingapp.config.DbConnection;
import com.joysistvi.recordingapp.controller.*;
import com.joysistvi.recordingapp.repository.*;
import com.joysistvi.recordingapp.service.*;

public class App {
    public static void main(String[] args) {
        DbConnection dbConnection = new DbConnection();

        //User
        UserRepo userRepo = new UserRepoImpl(dbConnection);
        UserService userService = new UserServiceImpl(userRepo);
        UserController userController = new UserController(userService);

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

        //PlaylistSong
        PlaylistSongRepo playlistSongRepo = new PlaylistSongRepoImpl(dbConnection);
        PlaylistSongService playlistSongService = new PlaylistSongServiceImpl(playlistSongRepo);
        PlaylistSongController playlistSongController = new PlaylistSongController(playlistSongService);

        //Playlist
        PlaylistRepo playlistRepo = new PlaylistRepoImpl(dbConnection);
        PlaylistService playlistService = new PlaylistServiceImpl(playlistRepo);
        PlaylistController playlistController = new PlaylistController(playlistService);
        PlaylistView playlistView = new PlaylistView(playlistController, playlistSongController, dbConnection);

        //Browse views
        BrowseArtistsView browseArtistsView = new BrowseArtistsView(artistController);
        BrowseAlbumsView  browseAlbumsView  = new BrowseAlbumsView(dbConnection);
        BrowseSongsView   browseSongsView   = new BrowseSongsView(dbConnection);

        //Dashboards
        AdminDashboardView adminDashboardView = new AdminDashboardView(
                artistView, albumView, songView);

        UserDashboardView userDashboardView = new UserDashboardView(
                browseArtistsView, browseAlbumsView, browseSongsView, playlistView);

        //Authentication
        RegisterView registerView = new RegisterView(userController);
        LoginView loginView = new LoginView(
                userController, registerView, adminDashboardView, userDashboardView);

        //Launch
        loginView.showLoginMenu();
    }
}