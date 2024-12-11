
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
    // Set custom font and colors
    Font customFont = new Font("Arial", Font.BOLD, 14);
    Color backgroundColor = new Color(230, 230, 250);
    Color buttonColor = new Color(123, 104, 238);
    Color textColor = Color.WHITE;

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
        addUserButton = createStyledButton("ADD User", customFont, buttonColor, backgroundColor);
        deleteUserButton = createStyledButton("Delete User", customFont, buttonColor, backgroundColor);
        updateUserButton = createStyledButton("Update User", customFont, buttonColor, backgroundColor);
        viewUsersButton = createStyledButton("View User", customFont, buttonColor, backgroundColor);
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
       // Create the frame
       JFrame frame = new JFrame("User Input Form");
       frame.setSize(400, 300);
       frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
       frame.setLocationRelativeTo(null); // Center the frame

       // Create a panel for input fields
       JPanel panel = new JPanel();
       panel.setLayout(new GridLayout(5, 2, 10, 10)); // Grid layout for form fields

       // Create input fields
       JLabel nameLabel = new JLabel("Enter User Name:");
       JTextField nameField = new JTextField();

       JLabel passwordLabel = new JLabel("Enter Password:");
       JPasswordField passwordField = new JPasswordField();

       JLabel roleLabel = new JLabel("Enter Role (e.g., TESTER, DEVELOPER, ADMIN):");
       JTextField roleField = new JTextField();

       JLabel idLabel = new JLabel("Enter ID:");
       JTextField idField = new JTextField();

       // Create a submit button
       JButton submitButton = createStyledButton("Submite", customFont,backgroundColor, buttonColor);

       // Add components to the panel
       panel.add(nameLabel);
       panel.add(nameField);
       panel.add(passwordLabel);
       panel.add(passwordField);
       panel.add(roleLabel);
       panel.add(roleField);
       panel.add(idLabel);
       panel.add(idField);
       panel.add(new JLabel()); // Empty label for spacing
       panel.add(submitButton);

       // Add the panel to the frame
       frame.add(panel);

       // Action listener for the submit button
       submitButton.addActionListener(e -> {
           // Get the user input
           String name = nameField.getText();
           String password = new String(passwordField.getPassword());
           String role = roleField.getText();
           int ID = Integer.parseInt(idField.getText());

           // Output user input (for demonstration)
           JOptionPane.showMessageDialog(frame, "Name: " + name + "\nPassword: " + password + "\nRole: " + role + "\nID: " + ID);
           users.addUser(name, ID, password, role); // Call the addUser method from users class
       });

       // Set the frame visible
       frame.setVisible(true);
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
    private JButton createStyledButton(String text, Font font, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        return button;
    }
}
