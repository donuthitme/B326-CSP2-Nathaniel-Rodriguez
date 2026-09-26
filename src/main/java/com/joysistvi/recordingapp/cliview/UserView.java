package com.joysistvi.recordingapp.cliview;

import com.joysistvi.recordingapp.controller.UserController;
import com.joysistvi.recordingapp.model.User;

import java.util.List;
import java.util.Scanner;

public class UserView {

    private final UserController controller;
    private final Scanner scanner;

    public UserView(UserController controller) {
        this.controller = controller;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        int choice;
        do {
            System.out.println("\n===== USER MANAGEMENT =====");
            System.out.println("1. View All Users");
            System.out.println("2. Search User By Username");
            System.out.println("3. Add User");
            System.out.println("4. Update User");
            System.out.println("5. Delete User");
            System.out.println("0. Back");
            System.out.print("Choice: ");

            choice = readInt();

            switch (choice) {
                case 1 -> viewAllUsers();
                case 2 -> searchUserByUsername();
                case 3 -> addUser();
                case 4 -> updateUser();
                case 5 -> deleteUser();
                case 0 -> System.out.println("Returning to main menu...");
                default -> System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 0);
    }

    private void viewAllUsers() {
        List<User> users = controller.getAllUsers();
        printUserTable(users);
    }

    private void searchUserByUsername() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();
        User user = controller.getUserByUsername(username);

        if (user == null) {
            System.out.println("No user found.");
            return;
        }
        printUserTable(List.of(user));
    }

    private void addUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();

        boolean success = controller.createUser(username, password);
        System.out.println(success ? "User added successfully." : "Failed to add user.");
    }

    private void updateUser() {
        System.out.print("Enter user ID to update: ");
        int id = readInt();
        System.out.print("Enter new username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter new password: ");
        String password = scanner.nextLine().trim();

        boolean success = controller.updateUser(id, username, password);
        System.out.println(success ? "User updated successfully." : "Failed to update user.");
    }

    private void deleteUser() {
        System.out.print("Enter user ID to delete: ");
        int id = readInt();
        boolean success = controller.deleteUser(id);
        System.out.println(success ? "User deleted." : "Failed to delete user.");
    }

    private void printUserTable(List<User> users) {
        if (users == null || users.isEmpty()) {
            System.out.println("No users found.");
            return;
        }

        System.out.println("\n----- User Table -----");
        System.out.println("+-------+----------------------+----------------------+");
        System.out.printf("| %-5s | %-20s | %-20s |%n", "ID", "Username", "Password");
        System.out.println("+-------+----------------------+----------------------+");

        for (User u : users) {
            System.out.printf("| %-5d | %-20s | %-20s |%n",
                    u.getId(),
                    truncate(u.getUsername(), 20),
                    truncate(u.getPassword(), 20));
        }

        System.out.println("+-------+----------------------+----------------------+");
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