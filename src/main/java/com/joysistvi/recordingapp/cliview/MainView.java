package com.joysistvi.recordingapp.cliview;

import java.util.Scanner;

public class MainView {

    private final ArtistView artistView;
    private final AlbumView albumView;
    private final SongView songView;
    private final PlaylistView playlistView;
    private final PlaylistSongView playlistSongView;
    private final UserView userView;
    private final Scanner scanner;

    public MainView(ArtistView artistView,
                    AlbumView albumView,
                    SongView songView,
                    PlaylistView playlistView,
                    PlaylistSongView playlistSongView,
                    UserView userView) {
        this.artistView = artistView;
        this.albumView = albumView;
        this.songView = songView;
        this.playlistView = playlistView;
        this.playlistSongView = playlistSongView;
        this.userView = userView;
        this.scanner = new Scanner(System.in);
    }

    public void showMainMenu() {
        int choice;
        do {
            System.out.println("\n===== RECORDING STUDIO APP =====");
            System.out.println("1. Artist Management");
            System.out.println("2. Album Management");
            System.out.println("3. Song Management");
            System.out.println("4. Playlist Management");
            System.out.println("5. PlaylistSong Management");
            System.out.println("6. User Management");
            System.out.println("0. Exit");
            System.out.print("Choice: ");

            choice = readInt();

            switch (choice) {
                case 1 -> artistView.showMenu();
                case 2 -> albumView.showMenu();
                case 3 -> songView.showMenu();
                case 4 -> playlistView.showMenu();
                case 5 -> playlistSongView.showMenu();
                case 6 -> userView.showMenu();
                case 0 -> System.out.println("Goodbye!");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 0);
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