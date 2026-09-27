package com.joysistvi.recordingapp.cliview;

import com.joysistvi.recordingapp.controller.UserController;
import com.joysistvi.recordingapp.model.User;

import java.util.Scanner;

public class LoginView {

    private final UserController userController;
    private final RegisterView registerView;
    private final AdminDashboardView adminDashboardView;
    private final UserDashboardView userDashboardView;
    private final Scanner scanner;

    public LoginView(UserController userController,
                     RegisterView registerView,
                     AdminDashboardView adminDashboardView,
                     UserDashboardView userDashboardView) {
        this.userController = userController;
        this.registerView = registerView;
        this.adminDashboardView = adminDashboardView;
        this.userDashboardView = userDashboardView;
        this.scanner = new Scanner(System.in);
    }

    public void showLoginMenu() {
        int choice;
        do {
            printBanner();
            System.out.println("  1. Login");
            System.out.println("  2. Register Account");
            System.out.println("  0. Exit");
            System.out.println("=================================");
            System.out.print("  Choice: ");

            choice = readInt();

            switch (choice) {
                case 1 -> handleLogin();
                case 2 -> registerView.showRegisterForm();
                case 0 -> System.out.println("\n  Goodbye! Exiting Recording Studio App.");
                default -> System.out.println("\n Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    private void handleLogin() {
        System.out.println("\n  ----- LOGIN -----");
        System.out.print("  Username: ");
        String username = scanner.nextLine().trim();

        System.out.print("  Password: ");
        String password = scanner.nextLine().trim();

        User user = userController.login(username, password);

        if (user == null) {
            System.out.println("\n Login failed. Please check your credentials.\n");
            return;
        }

        System.out.println("\n Welcome, " + user.getUsername() + "! (" + user.getRole() + ")");

        if (user.isAdmin()) {
            adminDashboardView.showMenu(user);
        } else {
            userDashboardView.showMenu(user);
        }
    }

    private void printBanner() {
        System.out.println("\n=================================");
        System.out.println("   RECORDING STUDIO APP");
        System.out.println("=================================");
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