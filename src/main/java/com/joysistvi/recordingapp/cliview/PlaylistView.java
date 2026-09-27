package com.joysistvi.recordingapp.cliview;

import com.joysistvi.recordingapp.config.DbConnection;
import com.joysistvi.recordingapp.controller.PlaylistController;
import com.joysistvi.recordingapp.controller.PlaylistSongController;
import com.joysistvi.recordingapp.model.Playlist;
import com.joysistvi.recordingapp.model.User;
import com.joysistvi.recordingapp.repository.SongRepo;
import com.joysistvi.recordingapp.repository.SongRepoImpl;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class PlaylistView {

    private final PlaylistController playlistController;
    private final PlaylistSongController playlistSongController;
    private final SongRepo songRepo;
    private final Scanner scanner;

    public PlaylistView(PlaylistController playlistController,
                        PlaylistSongController playlistSongController,
                        DbConnection dbConnection) {
        this.playlistController = playlistController;
        this.playlistSongController = playlistSongController;
        this.songRepo = new SongRepoImpl(dbConnection);
        this.scanner = new Scanner(System.in);
    }

    public void showMenu(User user) {
        int choice;
        do {
            printBanner(user);
            System.out.println("  1. View My Playlists");
            System.out.println("  2. Create Playlist");
            System.out.println("  3. Open Playlist");
            System.out.println("  4. Delete Playlist");
            System.out.println("  0. Back");
            System.out.println("=================================");
            System.out.print("  Choice: ");

            choice = readInt();

            switch (choice) {
                case 1 -> viewMyPlaylists(user);
                case 2 -> createPlaylist(user);
                case 3 -> openPlaylist(user);
                case 4 -> deletePlaylist(user);
                case 0 -> System.out.println("  Returning to User Dashboard...\n");
                default -> System.out.println("\n  Invalid choice.\n");
            }
        } while (choice != 0);
    }

    // ==================== VIEW ====================
    private void viewMyPlaylists(User user) {
        List<Playlist> playlists = playlistController.getPlaylistsByUser(user.getId());
        printPlaylistTable(playlists);
    }

    // ==================== CREATE ====================
    private void createPlaylist(User user) {
        // auto date = today
        Date today = new Date(System.currentTimeMillis());

        boolean success = playlistController.createPlaylist(today.toString(), user.getId());
        System.out.println(success ? "\n  Playlist created.\n" : "\n  [!] Failed to create playlist.\n");
    }

    // ==================== OPEN ====================
    private void openPlaylist(User user) {
        List<Playlist> mine = playlistController.getPlaylistsByUser(user.getId());
        if (mine == null || mine.isEmpty()) {
            System.out.println("\n  You have no playlists. Create one first.\n");
            return;
        }

        printPlaylistTable(mine);
        System.out.print("  Enter Playlist ID to open (0 to cancel): ");
        int playlistId = readInt();

        if (playlistId == 0) return;

        Playlist selected = null;
        for (Playlist p : mine) {
            if (p.getId() == playlistId) { selected = p; break; }
        }
        if (selected == null) {
            System.out.println("\n  [!] Playlist not found in your account.\n");
            return;
        }

        openPlaylistActions(user, selected);
    }

    private void openPlaylistActions(User user, Playlist playlist) {
        int choice;
        do {
            System.out.println("\n  ===== PLAYLIST #" + playlist.getId() + " =====");
            System.out.println("  1. View Songs in Playlist");
            System.out.println("  2. Add Song to Playlist");
            System.out.println("  3. Remove Song from Playlist");
            System.out.println("  0. Back");
            System.out.print("  Choice: ");

            choice = readInt();

            switch (choice) {
                case 1 -> viewSongsInPlaylist(playlist);
                case 2 -> addSongToPlaylist(playlist);
                case 3 -> removeSongFromPlaylist(playlist);
                case 0 -> System.out.println("  Closing playlist...\n");
                default -> System.out.println("\n  [!] Invalid choice.\n");
            }
        } while (choice != 0);
    }

    private void viewSongsInPlaylist(Playlist playlist) {
        List<Object[]> rows = playlistSongController.getSongsInPlaylistWithDetails(playlist.getId());
        if (rows == null || rows.isEmpty()) {
            System.out.println("\n  No songs in this playlist yet.\n");
            return;
        }

        System.out.println("\n  ----- SONGS IN PLAYLIST -----");
        System.out.println("  +------+----------------------+--------+----------------------+----------------------+");
        System.out.printf ("  | %-4s | %-20s | %-6s | %-20s | %-20s |%n",
                "ID", "Title", "Length", "Album", "Artist");
        System.out.println("  +------+----------------------+--------+----------------------+----------------------+");

        for (Object[] row : rows) {
            System.out.printf("  | %-4d | %-20s | %-6s | %-20s | %-20s |%n",
                    (int) row[0],
                    truncate((String) row[1], 20),
                    truncate((String) row[2], 6),
                    truncate((String) row[4], 20),
                    truncate((String) row[5], 20));
        }
        System.out.println("  +------+----------------------+--------+----------------------+----------------------+\n");
    }

    private void addSongToPlaylist(Playlist playlist) {
        List<Object[]> allSongs = songRepo.getAllSongsWithDetails();
        if (allSongs == null || allSongs.isEmpty()) {
            System.out.println("\n  No songs available.\n");
            return;
        }

        System.out.println("\n  ----- AVAILABLE SONGS -----");
        System.out.println("  +------+----------------------+----------------------+");
        System.out.printf ("  | %-4s | %-20s | %-20s |%n", "ID", "Title", "Artist");
        System.out.println("  +------+----------------------+----------------------+");

        for (Object[] row : allSongs) {
            System.out.printf("  | %-4d | %-20s | %-20s |%n",
                    (int) row[0],
                    truncate((String) row[1], 20),
                    truncate((String) row[5], 20));
        }
        System.out.println("  +------+----------------------+----------------------+");

        System.out.print("  Enter Song ID to add (0 to cancel): ");
        int songId = readInt();
        if (songId == 0) return;

        // check duplicate
        if (playlistSongController.isSongInPlaylist(playlist.getId(), songId)) {
            System.out.println("\n  Song is already in this playlist.\n");
            return;
        }

        boolean success = playlistSongController.addSongToPlaylist(playlist.getId(), songId);
        System.out.println(success ? "\n  Song added.\n" : "\n  [!] Failed to add song.\n");
    }

    private void removeSongFromPlaylist(Playlist playlist) {
        List<Object[]> rows = playlistSongController.getSongsInPlaylistWithDetails(playlist.getId());
        if (rows == null || rows.isEmpty()) {
            System.out.println("\n  No songs to remove.\n");
            return;
        }

        System.out.println("\n  ----- SONGS IN THIS PLAYLIST -----");
        System.out.println("  +------+----------------------+----------------------+");
        System.out.printf ("  | %-4s | %-20s | %-20s |%n", "ID", "Title", "Artist");
        System.out.println("  +------+----------------------+----------------------+");

        for (Object[] row : rows) {
            System.out.printf("  | %-4d | %-20s | %-20s |%n",
                    (int) row[0],
                    truncate((String) row[1], 20),
                    truncate((String) row[5], 20));
        }
        System.out.println("  +------+----------------------+----------------------+");

        System.out.print("  Enter Song ID to remove (0 to cancel): ");
        int songId = readInt();
        if (songId == 0) return;

        boolean success = playlistSongController.removeSongFromPlaylist(playlist.getId(), songId);
        System.out.println(success ? "\n  Song removed.\n" : "\n  [!] Song was not in this playlist.\n");
    }

    private void deletePlaylist(User user) {
        List<Playlist> mine = playlistController.getPlaylistsByUser(user.getId());
        if (mine == null || mine.isEmpty()) {
            System.out.println("\n  You have no playlists.\n");
            return;
        }

        printPlaylistTable(mine);
        System.out.print("  Enter Playlist ID to delete (0 to cancel): ");
        int playlistId = readInt();
        if (playlistId == 0) return;

        boolean owned = mine.stream().anyMatch(p -> p.getId() == playlistId);
        if (!owned) {
            System.out.println("\n  That playlist is not yours.\n");
            return;
        }

        System.out.print("  Are you sure? (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        if (!confirm.equals("y")) {
            System.out.println("  Cancelled.");
            return;
        }

        boolean success = playlistController.deletePlaylist(playlistId);
        System.out.println(success ? "\n  Playlist deleted.\n" : "\n  [!] Failed to delete.\n");
    }

    // ==================== TABLE ====================
    private void printPlaylistTable(List<Playlist> playlists) {
        if (playlists == null || playlists.isEmpty()) {
            System.out.println("\n  No playlists found.\n");
            return;
        }

        System.out.println("\n  ----- MY PLAYLISTS -----");
        System.out.println("  +------+--------------+");
        System.out.printf ("  | %-4s | %-12s |%n", "ID", "Date Created");
        System.out.println("  +------+--------------+");

        for (Playlist p : playlists) {
            System.out.printf("  | %-4d | %-12s |%n",
                    p.getId(),
                    p.getDateCreated() == null ? "-" : p.getDateCreated().toString());
        }
        System.out.println("  +------+--------------+\n");
    }

    private void printBanner(User user) {
        System.out.println("\n=================================");
        System.out.println("   MY PLAYLISTS");
        System.out.println("   User: " + user.getUsername());
        System.out.println("=================================");
    }

    private String truncate(String text, int max) {
        if (text == null) return "";
        if (text.length() <= max) return text;
        return text.substring(0, max - 3) + "...";
    }

    private int readInt() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("  Invalid number. Try again: ");
            }
        }
    }
}