/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bugtrackingsystem;


import java.util.ArrayList;

public class users extends person {
    public static ArrayList<person> humanList = new ArrayList<>();

    public users(String name, int ID) {
        super(name, ID);
        humanList.add(this); // Add user to the list
    }

    // Add a new user
    public static void addUser(String name, int ID, String password, String role) {
        // You may want to customize this logic (like password encryption, etc.)
        users newUser = new users(name, ID);
        newUser.setPassword(password);
        newUser.setRole(role);
        humanList.add(newUser);
    }

    // Authenticate the user
    public static person authenticateUser(String username, String password) {
        for (person user : humanList) {
            if (user.getName().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null; // If authentication fails
    }

    // Method to get user by ID for editing or deletion
    public static person getUserByID(int userID) {
        for (person user : humanList) {
            if (user.getID() == userID) {
                return user;
            }
        }
        return null; // If user not found
    }
}

