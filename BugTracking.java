import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class BugTracking {
    // Static arrays for user data
    private static String[] usernames = new String[10];
    private static String[] passwords = new String[10];
    private static String[] roles = new String[10];
    private static int userCount = 0;

    public static void main(String[] args) {
        // Load predefined users
        loadUsers();

        // Show login screen
        showLoginScreen();
    }

    private static void loadUsers() {
        // Predefined users
        usernames[0] = "admin";
        passwords[0] = "admin123";
        roles[0] = "ADMIN";

        usernames[1] = "tester";
        passwords[1] = "test123";
        roles[1] = "TESTER";

        usernames[2] = "developer";
        passwords[2] = "dev123";
        roles[2] = "DEVELOPER";

        usernames[3] = "manager";
        passwords[3] = "manager123";
        roles[3] = "PROJECT_MANAGER";

        userCount = 4; // Total predefined users
    }

    private static void showLoginScreen() {
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        Object[] message = {
            "Username:", usernameField,
            "Password:", passwordField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "BugTrackingSystem", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (authenticateUser(username, password)) {
                String role = getUserRole(username);
                JOptionPane.showMessageDialog(null, "Login Successful! Role: " + role);
                openUserModule(role);
            } else {
                JOptionPane.showMessageDialog(null, "Invalid Username or Password!", "Error", JOptionPane.ERROR_MESSAGE);
                showLoginScreen(); // Retry
            }
        }
    }

    private static boolean authenticateUser(String username, String password) {
        for (int i = 0; i < userCount; i++) {
            if (usernames[i].equals(username) && passwords[i].equals(password)) {
                return true;
            }
        }
        return false;
    }

    private static String getUserRole(String username) {
        for (int i = 0; i < userCount; i++) {
            if (usernames[i].equals(username)) {
                return roles[i];
            }
        }
        return "";
    }

    private static void openUserModule(String role) {
        switch (role) {
            case "TESTER":
                openTesterModule();
                break;
            case "DEVELOPER":
                openDeveloperModule();
                break;
            case "PROJECT_MANAGER":
                openProjectManagerModule();
                break;
            case "ADMIN":
                openAdminModule();
                break;
            default:
                JOptionPane.showMessageDialog(null, "Unauthorized Access");
        }
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
private static void openTesterModule() {
    JFrame frame = new JFrame("Tester Module");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setSize(600, 500);

    // Create inputs for bug details
    JTextField bugNameField = new JTextField(20);
    JTextField bugTypeField = new JTextField(20);
    JTextField priorityField = new JTextField(20);
    JTextField bugLevelField = new JTextField(20);
    JTextField projectNameField = new JTextField(20);
    JTextField bugDateField = new JTextField(20);
    JTextField statusField = new JTextField(20);
    JComboBox<String> developerComboBox = new JComboBox<>(new String[]{"developer", "anotherDeveloper"});

    // Create a button for attaching a screenshot
    JButton attachScreenshotButton = new JButton("Attach Screenshot");
    attachScreenshotButton.addActionListener(e -> {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
            JOptionPane.showMessageDialog(frame, "Screenshot attached: " + fileChooser.getSelectedFile().getName());
        }
    });

    // Create a button for submitting the bug
    JButton submitBugButton = new JButton("Submit Bug");
    submitBugButton.addActionListener(e -> {
        String bugName = bugNameField.getText();
        String bugType = bugTypeField.getText();
        String priority = priorityField.getText();
        String bugLevel = bugLevelField.getText();
        String projectName = projectNameField.getText();
        String bugDate = bugDateField.getText();
        String status = statusField.getText();
        String developerAssigned = (String) developerComboBox.getSelectedItem();

        // Save the bug to the bugs.txt file
        saveBugToFile(bugName, bugType, priority, bugLevel, projectName, bugDate, status, developerAssigned);
        JOptionPane.showMessageDialog(frame, "Bug submitted and assigned to developer!");

        // Send email notification to developer (assuming method `sendEmailNotification` is in place)
        sendEmailNotification("developer@example.com", bugName + " - " + bugType);
    });

    // Layout components
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.add(new JLabel("Bug Name:"));
    panel.add(bugNameField);
    panel.add(new JLabel("Bug Type:"));
    panel.add(bugTypeField);
    panel.add(new JLabel("Priority:"));
    panel.add(priorityField);
    panel.add(new JLabel("Bug Level:"));
    panel.add(bugLevelField);
    panel.add(new JLabel("Project Name:"));
    panel.add(projectNameField);
    panel.add(new JLabel("Bug Date:"));
    panel.add(bugDateField);
    panel.add(new JLabel("Status:"));
    panel.add(statusField);
    panel.add(new JLabel("Assign Developer:"));
    panel.add(developerComboBox);
    panel.add(attachScreenshotButton);
    panel.add(submitBugButton);

    frame.add(panel);
    frame.setVisible(true);
}


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
private static void openDeveloperModule() {
    // Example developer
    String developer = "developer";

    // Load bugs assigned to the developer
    String[][] developerBugs = loadDeveloperBugs(developer);

    // Display bugs in a JFrame
    JFrame frame = new JFrame("Developer Module");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setSize(400, 300);

    // Create a list of bug descriptions to show in JList
    String[] bugDescriptions = new String[developerBugs.length];
    for (int i = 0; i < developerBugs.length; i++) {
        bugDescriptions[i] = "Bug " + developerBugs[i][0] + ": " + developerBugs[i][1];  // Example format
    }

    // Bug List
    JLabel label = new JLabel("Assigned Bugs:");
    JList<String> bugList = new JList<>(bugDescriptions);
    JScrollPane bugScrollPane = new JScrollPane(bugList);

    // Button to mark a bug as complete
    JButton markAsCompleteButton = new JButton("Mark as Completed");
    markAsCompleteButton.addActionListener(e -> {
        String selectedBug = bugList.getSelectedValue();
        if (selectedBug != null) {
            JOptionPane.showMessageDialog(frame, "Bug marked as complete: " + selectedBug);
            sendEmailNotification("tester@example.com", selectedBug);
        } else {
            JOptionPane.showMessageDialog(frame, "Please select a bug to mark as complete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    });

    // Layout
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.add(label);
    panel.add(bugScrollPane);
    panel.add(markAsCompleteButton);

    frame.add(panel);
    frame.setVisible(true);
}

private static void sendEmailNotification(String testerEmail, String bugDetails) {
    // Replace the following with real email sending logic using Java Mail API
    System.out.println("Email sent to: " + testerEmail);
    System.out.println("Subject: Bug Status Update");
    System.out.println("Body: The bug has been marked as completed:\n\n" + bugDetails);
}


///////////////////////////////////////////////////////////////////////////////////
// Method to load bugs assigned to a developer
private static String[][] loadDeveloperBugs(String developer) {
    // Define a fixed-size array for bugs (assuming a max of 10 bugs for simplicity)
    String[][] bugs = new String[10][8];  // Assuming 8 fields for each bug
    int bugCount = 0;

    try (BufferedReader reader = new BufferedReader(new FileReader("bugs.txt"))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] bug = line.split(" ");  // Split by spaces (assuming space-separated format)
            if (bug.length >= 8 && bug[7].equals(developer)) { // Check if the developer assigned matches
                if (bugCount < bugs.length) {  // Ensure we don't exceed the array size
                    bugs[bugCount++] = bug;  // Add the bug to the array
                }
            }
        }
    } catch (IOException e) {
        System.out.println("Error loading bugs: " + e.getMessage());
    }

    // Return the array of bugs, truncated to the actual count
    String[][] result = new String[bugCount][8];
    System.arraycopy(bugs, 0, result, 0, bugCount);
    return result;
}



private static void saveBugToFile(String bugName, String bugType, String priority, String bugLevel,
                                  String projectName, String bugDate, String status, String developerAssigned) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("bugs.txt", true))) {
        // Write bug details to the text file, space-separated
        writer.write(String.join(" ", bugName, bugType, priority, bugLevel, projectName, bugDate, status, developerAssigned));
        writer.newLine();  // Add a newline after each bug
    } catch (IOException e) {
        System.out.println("Error saving bug: " + e.getMessage());
    }
}




    private static void openProjectManagerModule() {
        JOptionPane.showMessageDialog(null, "Welcome to the Project Manager Module!");
    }

    private static void openAdminModule() {
        JOptionPane.showMessageDialog(null, "Welcome to the Admin Module!");
    }
}
