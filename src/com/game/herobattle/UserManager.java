package com.game.herobattle;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UserManager {

    private final Map<String, User> users = new HashMap<>();
    private final Scanner scanner;

    public UserManager(Scanner scanner) {
        this.scanner = scanner;
    }
    public static class User {
        private final String name;
        private final int age;
        private final String password;

        public User(String name, int age, String password) {
            this.name = name;
            this.age = age;
            this.password = password;
        }

        public String getName() { return name; }
        public String getPassword() { return password; }
    }

    public boolean register() {
        System.out.println("=== Registration ===");
        System.out.print("Enter name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return false;
        }
        if (users.containsKey(name)) {
            System.out.println("This name is already registered. Use another name or log in.");
            return false;
        }
        System.out.print("Enter age: ");
        int age;
        try {
            age = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid age.");
            return false;
        }
        if (age < 15) {
            System.out.println("You are too young to play this game!");
            return false;
        }
        System.out.print("Enter password: ");
        String pwd = scanner.nextLine();
        users.put(name, new User(name, age, pwd));
        System.out.println("Registration successful for: " + name);
        return true;
    }

    public boolean login() {
        System.out.println("=== Log in ===");
        System.out.print("Enter name: ");
        String name = scanner.nextLine().trim();
        if (!users.containsKey(name)) {
            System.out.println("User not found. Please register first.");
            return false;
        }
        System.out.print("Enter password: ");
        String pwd = scanner.nextLine();
        User u = users.get(name);
        if (u.getPassword().equals(pwd)) {
            System.out.println("Login successful. Welcome, " + u.getName() + "!");
            return true;
        } else {
            System.out.println("Incorrect password.");
            return false;
        }
    }

    public User getUser(String name) {
        return users.get(name);
    }
}
