
package bugtrackingsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminGUI extends JFrame {

    private JButton addUserButton;
    private JButton deleteUserButton;
    private JButton updateUserButton;
    private JButton viewUsersButton;
    private JButton viewAllBugsButton;  // Add new button for viewing bugs
    private JTextArea textArea;

    public AdminGUI() {
        setTitle("Admin Panel");
        setSize(400, 500);  // Increased size to accommodate more buttons
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Layout setup
        setLayout(new BorderLayout());

        // Text area to display user info or bugs
        textArea = new JTextArea();
        textArea.setEditable(false);
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        // Panel for buttons
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));  // Added one extra row for the new button

        // Buttons for admin tasks
        addUserButton = new JButton("Add User");
        deleteUserButton = new JButton("Delete User");
        updateUserButton = new JButton("Update User");
        viewUsersButton = new JButton("View Users");
        viewAllBugsButton = new JButton("View All Bugs");  // New button

        panel.add(addUserButton);
        panel.add(deleteUserButton);
        panel.add(updateUserButton);
        panel.add(viewUsersButton);
        panel.add(viewAllBugsButton);  // Add to panel

        add(panel, BorderLayout.WEST);

        // Action Listeners
        addUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addUser();
            }
        });

        deleteUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteUser();
            }
        });

        updateUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateUser();
            }
        });

        viewUsersButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewUsers();
            }
        });

        viewAllBugsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewAllBugs();  // Action to view all bugs
            }
        });
    }

    // Method to add a user
    private void addUser() {
        String name = JOptionPane.showInputDialog(this, "Enter User Name:");
        String password = JOptionPane.showInputDialog(this, "Enter Password:");
        String role = JOptionPane.showInputDialog(this, "Enter Role (e.g., TESTER, DEVELOPER, ADMIN):");
        int ID = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter ID:"));
        
        users.addUser(name, ID, password, role); // Call the addUser method from users class
        JOptionPane.showMessageDialog(this, "User Added Successfully.");
    }

    // Method to delete a user
    private void deleteUser() {
        int ID = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter User ID to Delete:"));
        person user = users.getUserByID(ID); // Get the user by ID
        if (user != null) {
            users.humanList.remove(user); // Remove the user from the list
            JOptionPane.showMessageDialog(this, "User Deleted Successfully.");
        } else {
            JOptionPane.showMessageDialog(this, "User ID not found.");
        }
    }

    // Method to update a user
    private void updateUser() {
        int ID = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter User ID to Update:"));
        person user = users.getUserByID(ID); // Get the user by ID
        if (user != null) {
            String newName = JOptionPane.showInputDialog(this, "Enter New Name:");
            String newPassword = JOptionPane.showInputDialog(this, "Enter New Password:");
            String newRole = JOptionPane.showInputDialog(this, "Enter New Role:");
            
            user.setName(newName);
            user.setPassword(newPassword);
            user.setRole(newRole);
            JOptionPane.showMessageDialog(this, "User Updated Successfully.");
        } else {
            JOptionPane.showMessageDialog(this, "User ID not found.");
        }
    }

    // Method to view all users
    private void viewUsers() {
        textArea.setText("");
        if (users.humanList.isEmpty()) {
            textArea.setText("No users available.");
        } else {
            for (person user : users.humanList) {
                textArea.append("ID: " + user.getID() + " Name: " + user.getName() + " Role: " + user.getRole() + "\n");
            }
        }
    }

    // Method to view all bugs
    private void viewAllBugs() {
        StringBuilder bugList = new StringBuilder();

        // Loop through all bugs and add to output
        for (bugs bug : bugs.listBug) {
            bugList.append("Bug Name: ").append(bug.getBugName())
                   .append(", Type: ").append(bug.getBugType())
                   .append(", Priority: ").append(bug.getPriority())
                   .append(", Level: ").append(bug.getBugLevel())
                   .append(", Project: ").append(bug.getProjectName())
                   .append(", Status: ").append(bug.getStatus() ? "Open" : "Closed")
                   .append("\n")
                   .append("Date: ").append(bug.getBugDate())
                   .append("\n\n");
        }

        // Display all bugs in the output area
        textArea.setText(bugList.toString());
    }
}
