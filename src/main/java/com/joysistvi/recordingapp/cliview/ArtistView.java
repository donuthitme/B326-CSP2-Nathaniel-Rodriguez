package com.joysistvi.recordingapp.cliview;

import com.joysistvi.recordingapp.controller.ArtistsController;
import com.joysistvi.recordingapp.model.Artists;

import java.util.List;
import java.util.Scanner;

public class ArtistView {

    private final ArtistsController controller;
    private final Scanner scanner;

    public ArtistView(ArtistsController controller) {
        this.controller = controller;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        int choice;
        do {
            System.out.println("\n===== ARTIST MANAGEMENT =====");
            System.out.println("1. View All Artists");
            System.out.println("2. Search Artist");
            System.out.println("3. Add Artist");
            System.out.println("4. Update Artist");
            System.out.println("5. Archive Artist");
            System.out.println("6. Restore Artist");
            System.out.println("7. Delete Artist");
            System.out.println("0. Back");
            System.out.print("Choice: ");

            choice = readInt();

            switch (choice) {
                case 1 -> viewAllArtists();
                case 2 -> searchArtist();
                case 3 -> addArtist();
                case 4 -> updateArtist();
                case 5 -> archiveArtist();
                case 6 -> restoreArtist();
                case 7 -> deleteArtist();
                case 0 -> System.out.println("Returning to main menu...");
                default -> System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 0);
    }

    private void viewAllArtists() {
        List<Artists> artists = controller.getAllArtists();
        printArtistTable(artists);
    }

    private void searchArtist() {
        System.out.print("Enter keyword: ");
        String keyword = scanner.nextLine().trim();
        List<Artists> artists = controller.searchArtists(keyword);
        printArtistTable(artists);
    }

    private void addArtist() {
        System.out.print("Enter artist name: ");
        String name = scanner.nextLine().trim();
        boolean success = controller.createArtist(name);
        System.out.println(success ? "Artist added successfully." : "Failed to add artist.");
    }

    private void updateArtist() {
        System.out.print("Enter artist ID to update: ");
        int id = readInt();
        System.out.print("Enter new name: ");
        String name = scanner.nextLine().trim();
        boolean success = controller.updateArtist(id, name);
        System.out.println(success ? "Artist updated successfully." : "Failed to update artist.");
    }

    private void archiveArtist() {
        System.out.print("Enter artist ID to archive: ");
        int id = readInt();
        boolean success = controller.archiveArtist(id);
        System.out.println(success ? "Artist archived." : "Failed to archive artist.");
    }

    private void restoreArtist() {
        System.out.print("Enter artist ID to restore: ");
        int id = readInt();
        boolean success = controller.restoreArtist(id);
        System.out.println(success ? "Artist restored." : "Failed to restore artist.");
    }

    private void deleteArtist() {
        System.out.print("Enter artist ID to delete: ");
        int id = readInt();
        boolean success = controller.deleteArtist(id);
        System.out.println(success ? "Artist deleted." : "Failed to delete artist.");
    }

    // ---------- HELPERS ----------

    private void printArtistTable(List<Artists> artists) {
        if (artists == null || artists.isEmpty()) {
            System.out.println("No artists found.");
            return;
        }

        System.out.println("\n----- Artist Table -----");
        System.out.println("+-------+----------------------+");
        System.out.printf("| %-5s | %-20s |%n", "ID", "Name");
        System.out.println("+-------+----------------------+");

        for (Artists a : artists) {
            System.out.printf("| %-5d | %-20s |%n", a.getId(), a.getName());
        }

        System.out.println("+-------+----------------------+");
    }

    private int readInt() {
        while (true) {
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.print("Invalid number. Try again: ");
            }
        }
    }
}