package com.joysistvi.recordingapp.config;

import com.joysistvi.recordingapp.dao.ArtistsDao;

import java.sql.Connection;
import java.sql.SQLException;

public class Main extends DbConnection {
    public static void main(String[] args) {
        DbConnection dbConnection = new DbConnection();

        if (dbConnection.testConnection()) {
            System.out.println("Database connected successfully.");
        } else {
            System.out.println("Database connection failed.");
        }


//        ArtistsDao artistsDao = new ArtistsDao(dbConnection);
//        artistsDao.readAllArtists();
//        artistsDao.createArtist("Powfu");
//        artistsDao.updateArtist(23, "The Chainsmoker");
//        artistsDao.archiveArtist(26);
//        artistsDao.restoreArtist(24);
//        artistsDao.deleteArtist(19);
//        artistsDao.readArtistsById(2);
//        artistsDao.searchArtists("the");
    }
}
