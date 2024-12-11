
package bugtrackingsystem;
import javax.swing.*;
import java.awt.*;

public class BugTrackingSystem {
    // Other variables and methods

    private static String[] usernames = new String[10];
    private static String[] passwords = new String[10];
    private static String[] roles = new String[10];
    private static int userCount = 0;
    private static void loadSampleBugs() {
    // Creating and adding sample bugs to the list
    bugs bug1 = new bugs("UI issue", "UI Bug", 3, 1, "Calculate Perimeter", true);
    bugs bug2 = new bugs("Login Failure", "Authentication Bug", 1, 2, "User Authentication", true);
    bugs bug3 = new bugs("Slow response", "Performance Bug", 4, 3, "Database", false);
    bugs bug4 = new bugs("Text mismatch", "UI Bug", 2, 1, "Calculate Area", true);
    new tasks("Calculate Perimeter", "Task for perimeter calculation", 22,"Details about the task",1);
    new tasks("Calculate Area", "Task for area calculation",55, "Details about the task",2);
    new Developer(1, "John Doe", "john.doe@example.com");
    new Developer(2, "Jane Smith", "jane.smith@example.com");

    // Initialize tester
    tester testerInstance = new tester("Alice", 101, "alice@example.com");

    

}

    public static void main(String[] args) {
        // Load predefined users
        loadUsers();
        loadSampleBugs();

        // Show login screen
        showLoginScreen();
    }

    // Method to load users
    private static void loadUsers() {
        // Predefined users 
        users.addUser("admin", 1, "admin123", "ADMIN");
        users.addUser("tester", 2, "test123", "TESTER");
        users.addUser("developer", 3, "dev123", "DEVELOPER");
        users.addUser("manager", 4, "manager123", "PROJECT_MANAGER");
    }

   private static void showLoginScreen() {
    // Create the main frame
    JFrame frameLog = new JFrame();
    frameLog.setTitle("Welcome to Bug Tracking System");
    frameLog.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frameLog.setSize(420, 420);
    frameLog.setIconImage(new ImageIcon("logo.png").getImage());

    // Create a main panel to set the background color
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout());
    mainPanel.setBackground(new Color(123, 50, 250)); // Set background color here

    // Create title label
    JLabel titleLabel = new JLabel("LOGIN", JLabel.CENTER);
    titleLabel.setOpaque(true);
    titleLabel.setBackground(Color.LIGHT_GRAY);
    titleLabel.setForeground(Color.DARK_GRAY);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
    titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

    // Create username and password fields
    JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
    inputPanel.setOpaque(false); // Make the panel transparent to show the background
    JLabel usernameLabel = new JLabel("Username:");
    usernameLabel.setForeground(Color.WHITE);
    JTextField usernameField = new JTextField(15);
    usernameField.setPreferredSize(new Dimension(200, 30));
    JLabel passwordLabel = new JLabel("Password:");
    passwordLabel.setForeground(Color.WHITE);
    JPasswordField passwordField = new JPasswordField(15);
    passwordField.setPreferredSize(new Dimension(200, 30));
    inputPanel.add(usernameLabel);
    inputPanel.add(usernameField);
    inputPanel.add(passwordLabel);
    inputPanel.add(passwordField);

    // Create login and cancel buttons
    JButton loginButton = new JButton("Login");
    JButton cancelButton = new JButton("Cancel");
    JPanel buttonPanel = new JPanel();
    buttonPanel.setOpaque(false); // Make the panel transparent to show the background
    buttonPanel.add(loginButton);
    buttonPanel.add(cancelButton);

    // Set layout for the main panel
    mainPanel.add(titleLabel, BorderLayout.NORTH);
    mainPanel.add(inputPanel, BorderLayout.CENTER);
    mainPanel.add(buttonPanel, BorderLayout.SOUTH);

    // Add the main panel to the frame
    frameLog.setContentPane(mainPanel);

    // Add button actions
    loginButton.addActionListener(e -> {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (authenticateUser(username, password)) {
            String role = getUserRole(username);
            JOptionPane.showMessageDialog(frameLog, "Login Successful! Role: " + role);
            frameLog.dispose(); // Close login window
            openUserModule(role);
        } else {
            JOptionPane.showMessageDialog(frameLog, "Invalid Username or Password!", "Error", JOptionPane.ERROR_MESSAGE);
            usernameField.setText("");
            passwordField.setText("");
        }
    });

    cancelButton.addActionListener(e -> frameLog.dispose());

    // Make the frame visible
    frameLog.setVisible(true);
}


    private static boolean authenticateUser(String username, String password) {
        for (person user : users.humanList) {
            if (user.getName().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    private static String getUserRole(String username) {
        for (person user : users.humanList) {
            if (user.getName().equals(username)) {
                return user.getRole();
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
    
    private static void openAdminModule() {
        AdminGUI adminGUI = new AdminGUI();
        adminGUI.setVisible(true);
    }
    private static void openDeveloperModule() {
    // Create a sample Developer object 
    Developer developer = new Developer(1, "John Doe","John Doe@example.com");
    
    DeveloperGUI developerGUI = new DeveloperGUI(developer);
    developerGUI.setVisible(true);
}
    private static void openProjectManagerModule(){
        manager pm = new manager("PM", 1);
        ManagerGUI managerGUI = new ManagerGUI(pm);
        managerGUI.setVisible(true);
    }
    private static void openTesterModule(){
        tester TESTER = new tester("tester", 1,"TESTER");
        TesterGUI testerGUI = new TesterGUI(TESTER);
        testerGUI.setVisible(true);
    }

}
