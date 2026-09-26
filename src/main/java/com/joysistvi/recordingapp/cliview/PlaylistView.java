package com.joysistvi.recordingapp.cliview;

import com.joysistvi.recordingapp.controller.PlaylistController;
import com.joysistvi.recordingapp.model.Playlist;

import java.util.List;
import java.util.Scanner;

public class PlaylistView {

    private final PlaylistController controller;
    private final Scanner scanner;

    public PlaylistView(PlaylistController controller) {
        this.controller = controller;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        int choice;
        do {
            System.out.println("\n===== PLAYLIST MANAGEMENT =====");
            System.out.println("1. View All Playlists");
            System.out.println("2. View Playlists By User");
            System.out.println("3. Add Playlist");
            System.out.println("4. Update Playlist");
            System.out.println("5. Delete Playlist");
            System.out.println("0. Back");
            System.out.print("Choice: ");

            choice = readInt();

            switch (choice) {
                case 1 -> viewAllPlaylists();
                case 2 -> viewPlaylistsByUser();
                case 3 -> addPlaylist();
                case 4 -> updatePlaylist();
                case 5 -> deletePlaylist();
                case 0 -> System.out.println("Returning to main menu...");
                default -> System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 0);
    }

    private void viewAllPlaylists() {
        List<Playlist> playlists = controller.getAllPlaylists();
        printPlaylistTable(playlists);
    }

    private void viewPlaylistsByUser() {
        System.out.print("Enter user ID: ");
        int userId = readInt();
        List<Playlist> playlists = controller.getPlaylistsByUser(userId);
        printPlaylistTable(playlists);
    }

    private void addPlaylist() {
        System.out.print("Enter date created (yyyy-mm-dd): ");
        String dateCreated = scanner.nextLine().trim();
        System.out.print("Enter user ID: ");
        int userId = readInt();

        boolean success = controller.createPlaylist(dateCreated, userId);
        System.out.println(success ? "Playlist added successfully." : "Failed to add playlist.");
    }

    private void updatePlaylist() {
        System.out.print("Enter playlist ID to update: ");
        int id = readInt();
        System.out.print("Enter new date created (yyyy-mm-dd): ");
        String dateCreated = scanner.nextLine().trim();
        System.out.print("Enter new user ID: ");
        int userId = readInt();

        boolean success = controller.updatePlaylist(id, dateCreated, userId);
        System.out.println(success ? "Playlist updated successfully." : "Failed to update playlist.");
    }

    private void deletePlaylist() {
        System.out.print("Enter playlist ID to delete: ");
        int id = readInt();
        boolean success = controller.deletePlaylist(id);
        System.out.println(success ? "Playlist deleted." : "Failed to delete playlist.");
    }

    private void printPlaylistTable(List<Playlist> playlists) {
        if (playlists == null || playlists.isEmpty()) {
            System.out.println("No playlists found.");
            return;
        }

        System.out.println("\n----- Playlist Table -----");
        System.out.println("+-------+--------------+----------+");
        System.out.printf("| %-5s | %-12s | %-8s |%n", "ID", "Date Created", "User ID");
        System.out.println("+-------+--------------+----------+");

        for (Playlist p : playlists) {
            System.out.printf("| %-5d | %-12s | %-8d |%n",
                    p.getId(),
                    p.getDateCreated() == null ? "-" : p.getDateCreated().toString(),
                    p.getUserId());
        }

        System.out.println("+-------+--------------+----------+");
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