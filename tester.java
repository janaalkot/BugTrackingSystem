package bugtrackingsystem;

import java.util.ArrayList;
import java.util.List;

public class tester extends person {
    private String name;
    private String role;
    private int id;
    private static List<tester> allTesters = new ArrayList<>();

    public tester(String name, int id, String email) {
        this.name = name;
        this.id = id;
        this.role = email;  // Storing email as the role for simplicity
        allTesters.add(this);  // Add tester to the global list
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    // Create a bug with validation
    public bugs createBug(String bugName, String bugType, int priority, int bugLevel, String taskName, boolean status) {
        if (priority < 1 || priority > 5) {
            System.out.println("Error: Priority must be between 1 and 5.");
            return null;
        }
        if (bugName == null || bugName.isEmpty()) {
            System.out.println("Error: Bug name cannot be empty.");
            return null;
        }
        return new bugs(bugName, bugType, priority, bugLevel, taskName, status);
    }

    // Assign a bug to a developer
    public void assignBug(bugs bug, Developer developer, tasks task, String description) {
        if (bug == null || developer == null || task == null) {
            System.out.println("Error: Invalid bug, developer, or task.");
            return;
        }
        developer.receiveBug(bug, developer, task, description);
        sendEmail(developer.getEmail(), bug);
    }

    // Monitor open and closed bugs with details
    public String monitorBugs() {
        int openBugs = 0, closedBugs = 0;
        StringBuilder details = new StringBuilder();

        for (bugs bug : bugs.listBug) {
            if (bug.getStatus()) {
                openBugs++;
                details.append("Open Bug: ").append(bug.getBugName()).append("\n");
            } else {
                closedBugs++;
                details.append("Closed Bug: ").append(bug.getBugName()).append("\n");
            }
        }

        return "Open Bugs: " + openBugs + "\nClosed Bugs: " + closedBugs + "\nDetails:\n" + details;
    }

    // Simulate sending an email with validation
    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    private void sendEmail(String developerEmail, bugs bug) {
        if (!isValidEmail(developerEmail)) {
            System.out.println("Invalid email address: " + developerEmail);
            return;
        }
        System.out.println("Sending email to: " + developerEmail);
        System.out.println("Bug Details:\n" + bug.toString());
    }

    // Static method to return all testers
    public static List<tester> getAllTesters() {
        return allTesters;
    }
     public int getMonitoredBugsCount() {
        int openBugs = 0, closedBugs = 0;
        for (bugs bug : bugs.listBug) {
            if (bug.getStatus()) {  // If the bug is open
                openBugs++;
            } else {  // If the bug is closed
                closedBugs++;
            }
        }
        return openBugs + closedBugs;  // Return the total number of bugs monitored
    }
     
}
