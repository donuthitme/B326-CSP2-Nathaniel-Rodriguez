package com.joysistvi.recordingapp.cliview;

import com.joysistvi.recordingapp.config.DbConnection;
import com.joysistvi.recordingapp.repository.SongRepo;
import com.joysistvi.recordingapp.repository.SongRepoImpl;

import java.util.List;
import java.util.Scanner;

public class BrowseSongsView {

    private final SongRepo songRepo;
    private final Scanner scanner;

    public BrowseSongsView(DbConnection dbConnection) {
        this.songRepo = new SongRepoImpl(dbConnection);
        this.scanner = new Scanner(System.in);
    }

    public void showAll() {
        printTable(songRepo.getAllSongsWithDetails(), "BROWSE SONGS");
    }

    public void search() {
        System.out.print("\n  Enter search keyword (title or genre): ");
        String keyword = scanner.nextLine().trim();

        if (keyword.isEmpty()) {
            System.out.println("  [!] Keyword cannot be empty.\n");
            return;
        }
        printTable(songRepo.searchSongsWithDetails(keyword), "SEARCH RESULTS: " + keyword);
    }

    private void printTable(List<Object[]> rows, String title) {
        if (rows == null || rows.isEmpty()) {
            System.out.println("\n  No songs found.\n");
            return;
        }

        System.out.println("\n  ----- " + title + " -----");
        System.out.println("  +------+----------------------+--------+----------+----------------------+----------------------+");
        System.out.printf ("  | %-4s | %-20s | %-6s | %-8s | %-20s | %-20s |%n",
                "ID", "Title", "Length", "Genre", "Album", "Artist");
        System.out.println("  +------+----------------------+--------+----------+----------------------+----------------------+");

        for (Object[] row : rows) {
            System.out.printf("  | %-4d | %-20s | %-6s | %-8s | %-20s | %-20s |%n",
                    (int) row[0],
                    truncate((String) row[1], 20),
                    truncate((String) row[2], 6),
                    truncate((String) row[3], 8),
                    truncate((String) row[4], 20),
                    truncate((String) row[5], 20));
        }
        System.out.println("  +------+----------------------+--------+----------+----------------------+----------------------+\n");
    }

    private String truncate(String text, int max) {
        if (text == null) return "";
        if (text.length() <= max) return text;
        return text.substring(0, max - 3) + "...";
    }
}