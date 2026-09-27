package com.joysistvi.recordingapp.cliview;

import com.joysistvi.recordingapp.controller.UserController;

import java.util.Scanner;

public class RegisterView {

    private final UserController userController;
    private final Scanner scanner;

    public RegisterView(UserController userController) {
        this.userController = userController;
        this.scanner = new Scanner(System.in);
    }

    public void showRegisterForm() {
        System.out.println("\n  ----- REGISTER NEW ACCOUNT -----");
        System.out.println("  (New accounts are USER role by default)");

        System.out.print("  Username: ");
        String username = scanner.nextLine().trim();

        System.out.print("  Password: ");
        String password = scanner.nextLine().trim();

        System.out.print("  Confirm Password: ");
        String confirm = scanner.nextLine().trim();

        if (!password.equals(confirm)) {
            System.out.println("\n  Passwords do not match. Registration cancelled.\n");
            return;
        }

        boolean success = userController.register(username, password);

        if (success) {
            System.out.println("\n  Account created successfully! You can now log in.\n");
        } else {
            System.out.println("\n  Registration failed. See message above.\n");
        }
    }
}