package com.joysistvi.recordingapp.cliview;

import com.joysistvi.recordingapp.model.User;

import java.util.Scanner;

public class AdminDashboardView {

    private final ArtistView artistView;
    private final AlbumView albumView;
    private final SongView songView;
    private final Scanner scanner;

    public AdminDashboardView(ArtistView artistView,
                              AlbumView albumView,
                              SongView songView) {
        this.artistView = artistView;
        this.albumView = albumView;
        this.songView = songView;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu(User user) {
        int choice;
        do {
            printBanner(user);
            System.out.println("  1. Manage Artists");
            System.out.println("  2. Manage Albums");
            System.out.println("  3. Manage Songs");
            System.out.println("  0. Logout");
            System.out.println("=================================");
            System.out.print("  Choice: ");

            choice = readInt();

            switch (choice) {
                case 1 -> artistView.showMenu();
                case 2 -> albumView.showMenu();
                case 3 -> songView.showMenu();
                case 0 -> System.out.println("\n  Logging out...\n");
                default -> System.out.println("\n Invalid choice. Please try again.\n");
            }
        } while (choice != 0);
    }

    private void printBanner(User user) {
        System.out.println("\n=================================");
        System.out.println("   ADMIN DASHBOARD");
        System.out.println("   Welcome, " + user.getUsername() + "!");
        System.out.println("=================================");
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