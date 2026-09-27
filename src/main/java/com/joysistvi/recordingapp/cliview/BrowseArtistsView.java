package com.joysistvi.recordingapp.cliview;

import com.joysistvi.recordingapp.controller.ArtistsController;
import com.joysistvi.recordingapp.model.Artists;

import java.util.List;

public class BrowseArtistsView {

    private final ArtistsController artistsController;

    public BrowseArtistsView(ArtistsController artistsController) {
        this.artistsController = artistsController;
    }

    public void show() {
        List<Artists> artists = artistsController.getAllArtists();
        printTable(artists);
    }

    private void printTable(List<Artists> artists) {
        if (artists == null || artists.isEmpty()) {
            System.out.println("\n  No artists found.\n");
            return;
        }

        System.out.println("\n  ----- BROWSE ARTISTS -----");
        System.out.println("  +------+---------------------------+");
        System.out.printf ("  | %-4s | %-25s |%n", "ID", "Artist Name");
        System.out.println("  +------+---------------------------+");

        for (Artists a : artists) {
            System.out.printf("  | %-4d | %-25s |%n",
                    a.getId(),
                    truncate(a.getName(), 25));
        }
        System.out.println("  +------+---------------------------+\n");
    }

    private String truncate(String text, int max) {
        if (text == null) return "";
        if (text.length() <= max) return text;
        return text.substring(0, max - 3) + "...";
    }
}