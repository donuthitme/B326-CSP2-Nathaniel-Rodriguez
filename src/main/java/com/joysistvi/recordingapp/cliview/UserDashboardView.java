package com.joysistvi.recordingapp.cliview;

import com.joysistvi.recordingapp.model.User;

import java.util.Scanner;

public class UserDashboardView {

    private final BrowseArtistsView browseArtistsView;
    private final BrowseAlbumsView browseAlbumsView;
    private final BrowseSongsView browseSongsView;
    private final PlaylistView playlistView;
    private final Scanner scanner;

    public UserDashboardView(BrowseArtistsView browseArtistsView,
                             BrowseAlbumsView browseAlbumsView,
                             BrowseSongsView browseSongsView,
                             PlaylistView playlistView) {
        this.browseArtistsView = browseArtistsView;
        this.browseAlbumsView = browseAlbumsView;
        this.browseSongsView = browseSongsView;
        this.playlistView = playlistView;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu(User user) {
        int choice;
        do {
            printBanner(user);
            System.out.println("  1. Browse Artists");
            System.out.println("  2. Browse Albums");
            System.out.println("  3. Browse Songs");
            System.out.println("  4. Search Songs");
            System.out.println("  5. My Playlists");
            System.out.println("  0. Logout");
            System.out.println("=================================");
            System.out.print("  Choice: ");

            choice = readInt();

            switch (choice) {
                case 1 -> browseArtistsView.show();
                case 2 -> browseAlbumsView.show();
                case 3 -> browseSongsView.showAll();
                case 4 -> browseSongsView.search();
                case 5 -> playlistView.showMenu(user);
                case 0 -> System.out.println("\n  Logging out...\n");
                default -> System.out.println("\n  Invalid choice. Please try again.\n");
            }
        } while (choice != 0);
    }

    private void printBanner(User user) {
        System.out.println("\n=================================");
        System.out.println("   USER DASHBOARD");
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