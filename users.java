/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bugtrackingsystem;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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

    // Write user details to user.txt
    File file = new File("user.txt");
        PrintWriter writer = null;

        try {
            // Create the file if it doesn't exist
            if (!file.exists()) {
                file.createNewFile();
            }

            // Initialize PrintWriter in append mode
            writer = new PrintWriter(new FileWriter(file, true)); // Append mode
            String userData = name + "," + ID + "," + password + "," + role;
            writer.println(userData); // Use println to add a new line automatically
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file: " + e.getMessage());
        } finally {
            if (writer != null) {
                writer.close(); // Ensure the writer is closed to free resources
            }
        }
}
public static boolean deleteUser(int userID) {
        person user = getUserByID(userID); // Get the user by ID

        if (user != null) {
            // Remove the user from the list
            humanList.remove(user);

            // Remove the user from the file
            removeUserFromFile(userID);

            return true; // User deleted successfully
        }

        return false; // User not found
    }

    // Helper method to remove the user from the file
    private static void removeUserFromFile(int userID) {
        File file = new File("user.txt");
        File tempFile = new File("user_temp.txt");
        BufferedReader reader = null;
        PrintWriter writer = null;

        try {
            // Initialize reader and writer
            reader = new BufferedReader(new FileReader(file));
            writer = new PrintWriter(new FileWriter(tempFile));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                int id = Integer.parseInt(userData[1]);

                // If the ID doesn't match, write the line to the temp file
                if (id != userID) {
                    writer.println(line);
                }
            }

            // Delete the original file and rename the temp file
            if (file.delete()) {
                tempFile.renameTo(file);
            }

        } catch (IOException e) {
            System.err.println("An error occurred while removing the user from the file: " + e.getMessage());
        } finally {
            try {
                if (reader != null) reader.close();
                if (writer != null) writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

    public static void updateUserInFile(int userID, String newName, String newPassword, String newRole) {
        File file = new File("user.txt");
        File tempFile = new File("user_temp.txt");
        BufferedReader reader = null;
        PrintWriter writer = null;
    
        try {
            // Initialize reader and writer
            reader = new BufferedReader(new FileReader(file));
            writer = new PrintWriter(new FileWriter(tempFile));
    
            String line;
            boolean userFound = false;
    
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                int id = Integer.parseInt(userData[1]);
    
                // Check if this is the user to update
                if (id == userID) {
                    userFound = true;
                    // Write the updated user information to the temp file
                    writer.println(userData[0] + "," + userID + "," + newName + "," + newPassword + "," + newRole);
                } else {
                    // Write the original line to the temp file
                    writer.println(line);
                }
            }
    
            if (!userFound) {
                System.out.println("User ID not found in the file.");
            }
    
            // Delete the original file and rename the temp file
            if (file.delete()) {
                tempFile.renameTo(file);
            }
    
        } catch (IOException e) {
            System.err.println("An error occurred while updating the user in the file: " + e.getMessage());
        } finally {
            try {
                if (reader != null) reader.close();
                if (writer != null) writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    
}

