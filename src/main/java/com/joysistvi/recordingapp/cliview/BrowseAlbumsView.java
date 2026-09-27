package com.joysistvi.recordingapp.cliview;

import com.joysistvi.recordingapp.config.DbConnection;
import com.joysistvi.recordingapp.repository.AlbumRepo;
import com.joysistvi.recordingapp.repository.AlbumRepoImpl;

import java.util.List;

public class BrowseAlbumsView {

    private final AlbumRepo albumRepo;

    public BrowseAlbumsView(DbConnection dbConnection) {
        this.albumRepo = new AlbumRepoImpl(dbConnection);
    }

    public void show() {
        List<Object[]> rows = albumRepo.getAllAlbumsWithArtist();

        if (rows == null || rows.isEmpty()) {
            System.out.println("\n  No albums found.\n");
            return;
        }

        System.out.println("\n  ----- BROWSE ALBUMS -----");
        System.out.println("  +------+---------------------------+------+---------------------------+");
        System.out.printf ("  | %-4s | %-25s | %-4s | %-25s |%n", "ID", "Album", "Year", "Artist");
        System.out.println("  +------+---------------------------+------+---------------------------+");

        for (Object[] row : rows) {
            int id           = (int) row[0];
            String name      = (String) row[1];
            int year         = (int) row[2];
            String artist    = (String) row[3];

            System.out.printf("  | %-4d | %-25s | %-4d | %-25s |%n",
                    id, truncate(name, 25), year, truncate(artist, 25));
        }
        System.out.println("  +------+---------------------------+------+---------------------------+\n");
    }

    private String truncate(String text, int max) {
        if (text == null) return "";
        if (text.length() <= max) return text;
        return text.substring(0, max - 3) + "...";
    }
}