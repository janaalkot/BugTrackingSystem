package bugtrackingsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TesterGUI extends JFrame {
       private JTextArea outputArea;
    private JButton viewBugsButton, checkBugsButton, createBugButton, assignBugButton, monitorBugsButton, attachScreenshotButton, clearButton;
    private JComboBox<String> taskComboBox;
    private tester tester;

    public TesterGUI(tester tester) {
        this.tester = tester;

        // Set title, size, and close operation
        setTitle("Tester Dashboard");
        setSize(1300,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set custom font and colors
        Font customFont = new Font("Arial", Font.BOLD, 14);
        Color backgroundColor = new Color(230, 230, 250);
        Color buttonColor = new Color(123, 104, 238);
        Color textColor = Color.WHITE;

        // Create output area
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(customFont);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        // Create buttons with custom styling
        viewBugsButton = createStyledButton("View Bugs", customFont, buttonColor, textColor);
        checkBugsButton = createStyledButton("Check Bugs", customFont, buttonColor, textColor);
        createBugButton = createStyledButton("Create Bug", customFont, buttonColor, textColor);
        assignBugButton = createStyledButton("Assign Bug", customFont, buttonColor, textColor);
        monitorBugsButton = createStyledButton("Monitor Bugs", customFont, buttonColor, textColor);
        attachScreenshotButton = createStyledButton("Attach Screenshot", customFont, buttonColor, textColor);
        clearButton = createStyledButton("Clear", customFont, buttonColor, textColor);

        // Create task combo box
        String[] taskNames = {"Select Task", "Calculate Perimeter", "Calculate Area", "Other Task"};
        taskComboBox = new JComboBox<>(taskNames);
        taskComboBox.setFont(customFont);

        // Create panel for buttons and combo box
        JPanel panel = new JPanel();
        panel.setBackground(backgroundColor);
        panel.setLayout(new FlowLayout());
        panel.add(taskComboBox);
        panel.add(viewBugsButton);
        panel.add(checkBugsButton);
        panel.add(createBugButton);
        panel.add(assignBugButton);
        panel.add(monitorBugsButton);
        panel.add(attachScreenshotButton);
        panel.add(clearButton);

        // Add components to frame
        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Add action listeners to buttons
        viewBugsButton.addActionListener(e -> viewBugs());
        checkBugsButton.addActionListener(e -> checkBugs());
        createBugButton.addActionListener(e -> openTesterModule());
        assignBugButton.addActionListener(e -> assignBugAction());
        monitorBugsButton.addActionListener(e -> monitorBugs());
        attachScreenshotButton.addActionListener(e -> attachScreenshot());
        clearButton.addActionListener(e -> outputArea.setText(""));
        
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

    private void viewBugs() {
        if (bugs.listBug.isEmpty()) {
            outputArea.setText("No bugs available to view.");
            return;
        }
        StringBuilder bugList = new StringBuilder();
        for (bugs bug : bugs.listBug) {
            bugList.append("Bug Name: ").append(bug.getBugName())
                   .append(", Status: ").append(bug.getStatus() ? "Open" : "Closed")
                   .append("\n");
        }
        outputArea.setText(bugList.toString());
    }

    private void checkBugs() {
        outputArea.setText("Check Bugs functionality is under development.");
    }

    private void assignBugAction() {
        String taskName = (String) taskComboBox.getSelectedItem();
        if ("Select Task".equals(taskName)) {
            outputArea.setText("Please select a valid task.");
            return;
        }

        tasks selectedTask = null;
        for (tasks task : tasks.listTasks) {
            if (task.getTaskName().equalsIgnoreCase(taskName)) {
                selectedTask = task;
                break;
            }
        }

        if (selectedTask == null) {
            outputArea.setText("Task not found in the system.");
            return;
        }

        String[] developerNames = Developer.getAllDevelopers().stream()
                                           .map(Developer::getName)
                                           .toArray(String[]::new);
        String selectedDevName = (String) JOptionPane.showInputDialog(
                this,
                "Select a Developer:",
                "Assign Bug",
                JOptionPane.QUESTION_MESSAGE,
                null,
                developerNames,
                developerNames.length > 0 ? developerNames[0] : null);

        if (selectedDevName == null || selectedDevName.isEmpty()) {
            outputArea.setText("No developer selected.");
            return;
        }

        Developer selectedDeveloper = Developer.getAllDevelopers().stream()
                                               .filter(dev -> dev.getName().equals(selectedDevName))
                                               .findFirst()
                                               .orElse(null);

        if (selectedDeveloper == null) {
            outputArea.setText("Developer not found.");
            return;
        }

        bugs newBug = tester.createBug(
                "Sample Bug", "UI Bug", 3, 1, selectedTask.getTaskName(), true);

        if (newBug != null) {
            tester.assignBug(newBug, selectedDeveloper, selectedTask, "Bug description goes here.");
            outputArea.setText("Bug successfully assigned to " + selectedDeveloper.getName());
        } else {
            outputArea.setText("Failed to create bug. Please check inputs.");
        }
    }

    private void monitorBugs() {
        if (bugs.listBug.isEmpty()) {
            outputArea.setText("No bugs to monitor.");
            return;
        }
        String bugReport = tester.monitorBugs();
        outputArea.setText(bugReport);
    }

    private void attachScreenshot() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            String screenshotPath = fileChooser.getSelectedFile().getAbsolutePath();
            outputArea.setText("Screenshot attached: " + screenshotPath);
        }
    }

    private void openTesterModule() {
        JFrame frame = new JFrame("Tester Module");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 500);

        JTextField bugNameField = new JTextField(20);
        JTextField bugTypeField = new JTextField(20);
        JTextField priorityField = new JTextField(20);
        JTextField bugLevelField = new JTextField(20);
        JTextField projectNameField = new JTextField(20);
        JTextField bugDateField = new JTextField(20);
        JTextField statusField = new JTextField(20);
        JComboBox<String> developerComboBox = new JComboBox<>(new String[]{"developer", "anotherDeveloper"});

        JButton attachScreenshotButton = new JButton("Attach Screenshot");
        attachScreenshotButton.addActionListener(e -> attachScreenshot());

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

            saveBugToFile(bugName, bugType, priority, bugLevel, projectName, bugDate, status, developerAssigned);
            JOptionPane.showMessageDialog(frame, "Bug submitted and assigned to developer!");
            sendEmailNotification("developer@example.com", bugName + " - " + bugType);
        });

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

    private void saveBugToFile(String bugName, String bugType, String priority, String bugLevel, String projectName, String bugDate, String status, String developerAssigned) {
        System.out.println("Bug saved: " + bugName);
    }

    private void sendEmailNotification(String recipientEmail, String bugDetails) {
        System.out.println("Email sent to " + recipientEmail + ": " + bugDetails);
    }
}
