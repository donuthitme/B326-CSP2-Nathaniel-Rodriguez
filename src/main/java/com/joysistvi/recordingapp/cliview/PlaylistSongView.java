package com.joysistvi.recordingapp.cliview;

import com.joysistvi.recordingapp.controller.PlaylistSongController;
import com.joysistvi.recordingapp.model.PlaylistSong;

import java.util.List;
import java.util.Scanner;

public class PlaylistSongView {

    private final PlaylistSongController controller;
    private final Scanner scanner;

    public PlaylistSongView(PlaylistSongController controller) {
        this.controller = controller;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        int choice;
        do {
            System.out.println("\n===== PLAYLIST SONG MANAGEMENT =====");
            System.out.println("1. View All Playlist Songs");
            System.out.println("2. View Songs By Playlist");
            System.out.println("3. Add Song To Playlist");
            System.out.println("4. Remove Song From Playlist");
            System.out.println("5. Delete PlaylistSong By ID");
            System.out.println("0. Back");
            System.out.print("Choice: ");

            choice = readInt();

            switch (choice) {
                case 1 -> viewAll();
                case 2 -> viewByPlaylist();
                case 3 -> addSongToPlaylist();
                case 4 -> removeSongFromPlaylist();
                case 5 -> deleteById();
                case 0 -> System.out.println("Returning to main menu...");
                default -> System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 0);
    }

    private void viewAll() {
        List<PlaylistSong> list = controller.getAllPlaylistSongs();
        printPlaylistSongTable(list);
    }

    private void viewByPlaylist() {
        System.out.print("Enter playlist ID: ");
        int playlistId = readInt();
        List<PlaylistSong> list = controller.getSongsByPlaylist(playlistId);
        printPlaylistSongTable(list);
    }

    private void addSongToPlaylist() {
        System.out.print("Enter playlist ID: ");
        int playlistId = readInt();
        System.out.print("Enter song ID: ");
        int songId = readInt();

        boolean success = controller.addSongToPlaylist(playlistId, songId);
        System.out.println(success
                ? "Song added to playlist successfully."
                : "Failed to add song to playlist.");
    }

    private void removeSongFromPlaylist() {
        System.out.print("Enter playlist ID: ");
        int playlistId = readInt();
        System.out.print("Enter song ID: ");
        int songId = readInt();

        boolean success = controller.removeSongFromPlaylist(playlistId, songId);
        System.out.println(success
                ? "Song removed from playlist."
                : "Failed to remove song from playlist.");
    }

    private void deleteById() {
        System.out.print("Enter PlaylistSong ID to delete: ");
        int id = readInt();
        boolean success = controller.deletePlaylistSong(id);
        System.out.println(success ? "PlaylistSong deleted." : "Failed to delete PlaylistSong.");
    }

    private void printPlaylistSongTable(List<PlaylistSong> list) {
        if (list == null || list.isEmpty()) {
            System.out.println("No playlist songs found.");
            return;
        }

        System.out.println("\n----- PlaylistSong Table -----");
        System.out.println("+-------+-------------+----------+");
        System.out.printf("| %-5s | %-11s | %-8s |%n", "ID", "Playlist ID", "Song ID");
        System.out.println("+-------+-------------+----------+");

        for (PlaylistSong ps : list) {
            System.out.printf("| %-5d | %-11d | %-8d |%n",
                    ps.getId(),
                    ps.getPlaylistId(),
                    ps.getSongId());
        }

        System.out.println("+-------+-------------+----------+");
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