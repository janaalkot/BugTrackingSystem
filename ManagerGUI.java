package bugtrackingsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Iterator;

public class ManagerGUI extends JFrame {
    private JTextArea outputArea;
    private JButton viewAllBugsButton, updateBugStatusButton, clearButton, checkPerformanceButton;
    private manager manager;
    private JPanel bugsPanel;
    private JComboBox<String> developerComboBox, testerComboBox;

    public ManagerGUI(manager manager) {
        this.manager = manager;

        // Manually adding developers and testers for testing
        new Developer(201, "Developer 1", "junior@example.com");
        new Developer(202, "Developer 2", "senior@example.com");
        new tester("Tester 1", 301, "tester1@example.com");
        new tester("Tester 2", 302, "tester2@example.com");

        setTitle("Manager Dashboard");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        outputArea.setPreferredSize(new Dimension(400,100));


        updateBugStatusButton = createStyledButton("Update Bug", customFont, buttonColor, textColor);
        clearButton = createStyledButton("Clear", customFont, buttonColor, textColor);
        checkPerformanceButton = createStyledButton("Check Preformance", customFont, buttonColor, textColor);

        bugsPanel = new JPanel();
        bugsPanel.setLayout(new BoxLayout(bugsPanel, BoxLayout.Y_AXIS));

        // Combo boxes for selecting Developer and Tester
        developerComboBox = new JComboBox<>();
        testerComboBox = new JComboBox<>();

        // Populate ComboBoxes dynamically
        populateDeveloperComboBox();
        populateTesterComboBox();

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(viewAllBugsButton);
        buttonPanel.add(updateBugStatusButton);
        buttonPanel.add(checkPerformanceButton);
        buttonPanel.add(clearButton);
        
        JPanel comboBoxPanel = new JPanel();
        comboBoxPanel.setLayout(new FlowLayout());
        comboBoxPanel.add(new JLabel("Select Developer:"));
        comboBoxPanel.add(developerComboBox);
        comboBoxPanel.add(new JLabel("Select Tester:"));
        comboBoxPanel.add(testerComboBox);

        add(buttonPanel, BorderLayout.NORTH);
        add(comboBoxPanel, BorderLayout.CENTER);
        add(outputArea, BorderLayout.SOUTH);

        viewAllBugsButton.addActionListener(e -> openBug());
        updateBugStatusButton.addActionListener(e -> updateBugStatus());
        checkPerformanceButton.addActionListener(e -> checkPerformance());
        clearButton.addActionListener(e -> outputArea.setText(""));
    }

 

    // Method to update the bug status based on the checkbox state
    private void updateBugStatus() {
        Component[] components = bugsPanel.getComponents();
        Iterator<bugs> iterator = bugs.listBug.iterator();
        int i = 0;
        while (iterator.hasNext() && i < components.length) {
            bugs bug = iterator.next();
            JCheckBox checkBox = (JCheckBox) components[i];
            bug.setStatus(checkBox.isSelected());
            i++;
        }
        outputArea.setText("Bug statuses have been updated.");
    }

    // Method to check the performance of selected Developer and Tester
    private void checkPerformance() {
        String selectedDeveloperName = (String) developerComboBox.getSelectedItem();
        String selectedTesterName = (String) testerComboBox.getSelectedItem();

        // Check if both developer and tester are selected
        if ("Select Developer".equals(selectedDeveloperName) || "Select Tester".equals(selectedTesterName)) {
            outputArea.setText("Please select both a Developer and a Tester.");
            return;
        }

        // Find the selected Developer and Tester
        Developer selectedDeveloper = findDeveloperByName(selectedDeveloperName);
        tester selectedTester = findTesterByName(selectedTesterName);

        if (selectedDeveloper != null && selectedTester != null) {
            // Get the number of bugs completed by Developer and monitored by Tester
            int developerBugsCompleted = selectedDeveloper.getCompletedBugsCount();
            int testerBugsMonitored = selectedTester.getMonitoredBugsCount();

            // Display the performance information
            outputArea.setPreferredSize(new Dimension(500, 200));
            outputArea.setText("Performance Report:\n\n");
            outputArea.append("Developer: " + selectedDeveloper.getName() + "\n");
            outputArea.append("Bugs Completed: " + developerBugsCompleted + "\n\n");

            outputArea.append("Tester: " + selectedTester.getName() + "\n");
            outputArea.append("Bugs Monitored: " + testerBugsMonitored + "\n");
        } else {
            outputArea.setText("Developer or Tester not found.");
        }
    }

    private Developer findDeveloperByName(String name) {
        for (Developer dev : Developer.getAllDevelopers()) {
            if (dev.getName().equalsIgnoreCase(name)) {
                return dev;
            }
        }
        return null;
    }

    private tester findTesterByName(String name) {
        for (tester t : tester.getAllTesters()) {
            if (t.getName().equalsIgnoreCase(name)) {
                return t;
            }
        }
        return null;
    }

    // Helper methods to populate ComboBoxes dynamically
    private void populateDeveloperComboBox() {
        developerComboBox.removeAllItems();
        developerComboBox.addItem("Select Developer");
        for (Developer dev : Developer.getAllDevelopers()) {
            developerComboBox.addItem(dev.getName());
        }
    }

    private void populateTesterComboBox() {
        testerComboBox.removeAllItems();
        testerComboBox.addItem("Select Tester");
        for (tester t : tester.getAllTesters()) {
            testerComboBox.addItem(t.getName());
        }
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
