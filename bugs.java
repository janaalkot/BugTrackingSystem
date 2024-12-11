package bugtrackingsystem;

import java.util.ArrayList;
import java.util.Date;

public class bugs {
    private String bugName;
    private String bugType;
    private int priority;
    private int bugLevel;
    private String projectName;
    private final Date bugDate; // Make the bug date final so it can't be modified after creation
    private boolean status;
    public static int bugNumber = 0; // Static bug number counter for all instances
    public static ArrayList<bugs> listBug = new ArrayList<>(); // List to hold all bugs

    // Constructor
    public bugs(String bugName, String bugType, int priority, int bugLevel, String projectName, boolean status) {
        this.bugName = bugName;
        this.bugType = bugType;
        this.priority = priority;
        this.bugLevel = bugLevel;
        this.projectName = projectName;
        this.status = status;
        this.bugDate = new Date(); // Set the current date when the bug is created
        bugNumber++; // Increment the global bug number each time a new bug is created
        listBug.add(this); // Add the bug to the global list
    }

    // Getter and Setter Methods
    public String getBugName() {
        return bugName;
    }

    public void setBugName(String bugName) {
        this.bugName = bugName;
    }

    public String getBugType() {
        return bugType;
    }

    public void setBugType(String bugType) {
        this.bugType = bugType;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getBugLevel() {
        return bugLevel;
    }

    public void setBugLevel(int bugLevel) {
        this.bugLevel = bugLevel;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getBugDate() {
        return bugDate;
    }

    // toString Method for displaying bug details
    @Override
    public String toString() {
        return "Bug Name: " + bugName + "\n" +
               "Bug Type: " + bugType + "\n" +
               "Priority: " + priority + "\n" +
               "Bug Level: " + bugLevel + "\n" +
               "Project: " + projectName + "\n" +
               "Status: " + (status ? "Open" : "Closed") + "\n" +
               "Date: " + bugDate + "\n" +
               "Bug Number: " + bugNumber;
    }
}
