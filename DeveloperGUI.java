package bugtrackingsystem;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.util.ArrayList;

public class DeveloperGUI extends JFrame {
    private Developer developer;
    private JTextArea outputArea;
    private JPanel bugPanel;
    private ArrayList<JCheckBox> bugCheckBoxes = new ArrayList<>();
    

    // Constructor to initialize DeveloperGUI with Developer instance
    public DeveloperGUI(Developer developer) {
        this.developer = developer;
        setTitle("Developer Module");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JButton viewTasksBtn = new JButton("View Assigned Tasks");
        JButton viewBugsBtn = new JButton("View Assigned Bugs");
        JButton fixBugBtn = new JButton("Fix Bug");

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        bugPanel = new JPanel();
        bugPanel.setLayout(new BoxLayout(bugPanel, BoxLayout.Y_AXIS));
        JScrollPane bugScrollPane = new JScrollPane(bugPanel);
        
        // Add Action Listeners
        viewTasksBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewAssignedTasks();
            }
        });
        viewBugsBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewAssignedBugs();
            }
        });
        fixBugBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fixBug();
            }
        });

        // Add buttons and the bug panel to the main panel
        panel.add(viewTasksBtn);
        panel.add(viewBugsBtn);
        panel.add(fixBugBtn);
        panel.add(bugScrollPane);
        panel.add(scrollPane);

        add(panel);  // Adding panel to the frame
    }

    // Method to view tasks assigned to this developer
    private void viewAssignedTasks() {
        StringBuilder taskDetails = new StringBuilder();
        for (tasks task : tasks.listTasks) {
            if (task.getTaskNumber() == developer.getID()) {
                taskDetails.append("Task Name: ").append(task.getTaskName())
                        .append("\nDescription: ").append(task.getTaskDescription())
                        .append("\nDetails: ").append(task.getDetails()).append("\n\n");
            }
        }
        // Updating the output area with task details
        outputArea.setText(taskDetails.toString());
    }

    // Method to view bugs assigned to this developer
    private void viewAssignedBugs() {
        bugCheckBoxes.clear();  // Clear existing checkboxes
        bugPanel.removeAll();   // Remove all existing bug checkboxes from the panel

        StringBuilder bugDetails = new StringBuilder();
        // Loop through the developer's assigned bugs
        for (bugs bug : developer.getAssignedBugs()) {
            // Creating a checkbox for each bug
            JCheckBox checkBox = new JCheckBox(bug.getBugName() + " - " + (bug.getStatus() ? "Open" : "Closed"));
            bugCheckBoxes.add(checkBox);  // Store checkboxes for later use
            bugPanel.add(checkBox);  // Add checkbox for each bug to the panel
        }

        // Refresh the bug panel with newly added checkboxes
        bugPanel.revalidate();  // Refresh the panel
        bugPanel.repaint();

        // Show a message if there are no bugs assigned
        if (developer.getAssignedBugs().isEmpty()) {
            bugDetails.append("No bugs assigned.");
        } else {
            for (bugs bug : developer.getAssignedBugs()) {
                bugDetails.append("Bug Name: ").append(bug.getBugName())
                        .append("\nBug Type: ").append(bug.getBugType())
                        .append("\nStatus: ").append(bug.getStatus() ? "Open" : "Closed")
                        .append("\n\n");
            }
        }

        // Updating the output area with bug details
        outputArea.setText(bugDetails.toString());
    }

    // Method to simulate fixing a bug
    private void fixBug() {
        // Iterate through the checkboxes to find selected ones
        for (int i = 0; i < bugCheckBoxes.size(); i++) {
            JCheckBox checkBox = bugCheckBoxes.get(i);
            if (checkBox.isSelected()) {
                bugs selectedBug = developer.getAssignedBugs().get(i);
                developer.fixBug(selectedBug);  // Fix the bug and remove it from assigned list
                JOptionPane.showMessageDialog(this, "Bug fixed successfully: " + selectedBug.getBugName());
            }
        }
        // Refresh the view after fixing bugs
        viewAssignedBugs();
    }
}
