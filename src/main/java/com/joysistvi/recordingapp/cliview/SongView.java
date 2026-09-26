package com.joysistvi.recordingapp.cliview;

import com.joysistvi.recordingapp.controller.SongController;
import com.joysistvi.recordingapp.model.Song;

import java.util.List;
import java.util.Scanner;

public class SongView {

    private final SongController controller;
    private final Scanner scanner;

    public SongView(SongController controller) {
        this.controller = controller;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        int choice;
        do {
            System.out.println("\n===== SONG MANAGEMENT =====");
            System.out.println("1. View All Songs");
            System.out.println("2. Search Song");
            System.out.println("3. View Songs By Album");
            System.out.println("4. Add Song");
            System.out.println("5. Update Song");
            System.out.println("6. Delete Song");
            System.out.println("0. Back");
            System.out.print("Choice: ");

            choice = readInt();

            switch (choice) {
                case 1 -> viewAllSongs();
                case 2 -> searchSong();
                case 3 -> viewSongsByAlbum();
                case 4 -> addSong();
                case 5 -> updateSong();
                case 6 -> deleteSong();
                case 0 -> System.out.println("Returning to main menu...");
                default -> System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 0);
    }

    private void viewAllSongs() {
        List<Song> songs = controller.getAllSongs();
        printSongTable(songs);
    }

    private void searchSong() {
        System.out.print("Enter keyword: ");
        String keyword = scanner.nextLine().trim();
        List<Song> songs = controller.searchSongs(keyword);
        printSongTable(songs);
    }

    private void viewSongsByAlbum() {
        System.out.print("Enter album ID: ");
        int albumId = readInt();
        List<Song> songs = controller.getSongsByAlbum(albumId);
        printSongTable(songs);
    }

    private void addSong() {
        System.out.print("Enter song title: ");
        String title = scanner.nextLine().trim();
        System.out.print("Enter length (e.g. 3:45): ");
        String length = scanner.nextLine().trim();
        System.out.print("Enter genre: ");
        String genre = scanner.nextLine().trim();
        System.out.print("Enter album ID: ");
        int albumId = readInt();

        boolean success = controller.createSong(title, length, genre, albumId);
        System.out.println(success ? "Song added successfully." : "Failed to add song.");
    }

    private void updateSong() {
        System.out.print("Enter song ID to update: ");
        int id = readInt();
        System.out.print("Enter new title: ");
        String title = scanner.nextLine().trim();
        System.out.print("Enter new length: ");
        String length = scanner.nextLine().trim();
        System.out.print("Enter new genre: ");
        String genre = scanner.nextLine().trim();
        System.out.print("Enter new album ID: ");
        int albumId = readInt();

        boolean success = controller.updateSong(id, title, length, genre, albumId);
        System.out.println(success ? "Song updated successfully." : "Failed to update song.");
    }

    private void deleteSong() {
        System.out.print("Enter song ID to delete: ");
        int id = readInt();
        boolean success = controller.deleteSong(id);
        System.out.println(success ? "Song deleted." : "Failed to delete song.");
    }

    private void printSongTable(List<Song> songs) {
        if (songs == null || songs.isEmpty()) {
            System.out.println("No songs found.");
            return;
        }

        System.out.println("\n----- Song Table -----");
        System.out.println("+-------+----------------------+--------+--------------+----------+");
        System.out.printf("| %-5s | %-20s | %-6s | %-12s | %-8s |%n",
                "ID", "Title", "Length", "Genre", "Album ID");
        System.out.println("+-------+----------------------+--------+--------------+----------+");

        for (Song s : songs) {
            System.out.printf("| %-5d | %-20s | %-6s | %-12s | %-8d |%n",
                    s.getId(),
                    truncate(s.getTitle(), 20),
                    truncate(s.getLength(), 6),
                    truncate(s.getGenre(), 12),
                    s.getAlbumId());
        }

        System.out.println("+-------+----------------------+--------+--------------+----------+");
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
                System.out.print("Invalid number. Try again: ");
            }
        }
    }
}