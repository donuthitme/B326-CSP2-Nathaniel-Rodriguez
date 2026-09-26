package com.joysistvi.recordingapp.cliview;

import com.joysistvi.recordingapp.controller.AlbumController;
import com.joysistvi.recordingapp.model.Album;

import java.util.List;
import java.util.Scanner;

public class AlbumView {

    private final AlbumController controller;
    private final Scanner scanner;

    public AlbumView(AlbumController controller) {
        this.controller = controller;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        int choice;
        do {
            System.out.println("\n===== ALBUM MANAGEMENT =====");
            System.out.println("1. View All Albums");
            System.out.println("2. Search Album");
            System.out.println("3. View Albums By Artist");
            System.out.println("4. Add Album");
            System.out.println("5. Update Album");
            System.out.println("6. Delete Album");
            System.out.println("0. Back");
            System.out.print("Choice: ");

            choice = readInt();

            switch (choice) {
                case 1 -> viewAllAlbums();
                case 2 -> searchAlbum();
                case 3 -> viewAlbumsByArtist();
                case 4 -> addAlbum();
                case 5 -> updateAlbum();
                case 6 -> deleteAlbum();
                case 0 -> System.out.println("Returning to main menu...");
                default -> System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 0);
    }

    private void viewAllAlbums() {
        List<Album> albums = controller.getAllAlbums();
        printAlbumTable(albums);
    }

    private void searchAlbum() {
        System.out.print("Enter keyword: ");
        String keyword = scanner.nextLine().trim();
        List<Album> albums = controller.searchAlbums(keyword);
        printAlbumTable(albums);
    }

    private void viewAlbumsByArtist() {
        System.out.print("Enter artist ID: ");
        int artistId = readInt();
        List<Album> albums = controller.getAlbumsByArtist(artistId);
        printAlbumTable(albums);
    }

    private void addAlbum() {
        System.out.print("Enter album name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter release year: ");
        int year = readInt();

        System.out.print("Enter artist ID: ");
        int artistId = readInt();

        boolean success = controller.createAlbum(name, year, artistId);
        System.out.println(success ? "Album added successfully." : "Failed to add album.");
    }

    private void updateAlbum() {
        System.out.print("Enter album ID to update: ");
        int id = readInt();

        System.out.print("Enter new name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter new release year: ");
        int year = readInt();

        System.out.print("Enter new artist ID: ");
        int artistId = readInt();

        boolean success = controller.updateAlbum(id, name, year, artistId);
        System.out.println(success ? "Album updated successfully." : "Failed to update album.");
    }

    private void deleteAlbum() {
        System.out.print("Enter album ID to delete: ");
        int id = readInt();
        boolean success = controller.deleteAlbum(id);
        System.out.println(success ? "Album deleted." : "Failed to delete album.");
    }

    private void printAlbumTable(List<Album> albums) {
        if (albums == null || albums.isEmpty()) {
            System.out.println("No albums found.");
            return;
        }

        System.out.println("\n----- Album Table -----");
        System.out.println("+-------+----------------------+------+-----------+");
        System.out.printf("| %-5s | %-20s | %-4s | %-9s |%n", "ID", "Name", "Year", "Artist ID");
        System.out.println("+-------+----------------------+------+-----------+");

        for (Album a : albums) {
            System.out.printf("| %-5d | %-20s | %-4d | %-9d |%n",
                    a.getId(), truncate(a.getName(), 20), a.getYear(), a.getArtistId());
        }

        System.out.println("+-------+----------------------+------+-----------+");
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