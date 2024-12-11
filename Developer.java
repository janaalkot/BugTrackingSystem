package bugtrackingsystem;

import java.util.ArrayList;
import java.util.List;

public class Developer extends person {
    private boolean flagReceiveBug = false;
    private boolean receiveTask = false;
    private ArrayList<bugs> fixedBugs = new ArrayList<>();
    private ArrayList<bugs> assignedBugs = new ArrayList<>();
    private ArrayList<tasks> assignedTasks = new ArrayList<>();
    private static List<Developer> allDevelopers = new ArrayList<>();
    private String email;

    public Developer(int ID, String name, String email) {
        super(name, ID);  // Calling the constructor of the superclass (person)
        this.email = email;
        allDevelopers.add(this);  // Add the developer to the static list of all developers
    }

    // View the list of assigned bugs to the developer
    public void viewAssignedBugs() {
        if (assignedBugs.isEmpty()) {
            System.out.println("No bugs assigned to " + getName() + ".");
            return;
        }
        for (bugs bug : assignedBugs) {
            System.out.println("Bug Name: " + bug.getBugName() + ", Bug Type: " + bug.getBugType());
        }
    }

    // Receive a bug and assign it to the developer
    public void receiveBug(bugs bug, Developer developer, tasks task, String description) {
        if (bug == null || task == null) {
            System.out.println("Error: Bug or Task cannot be null.");
            return;
        }

        System.out.println("Developer " + getName() + " received the bug: " + bug.getBugName());
        System.out.println("Bug Description: " + description);
        System.out.println("Associated Task: " + task.getTaskName());
        
        assignedBugs.add(bug);  // Add the bug to the developer's assigned bugs list
        flagReceiveBug = true;
    }

    public String getEmail() {
        return email;  
    }

    // Receive a task assigned to the developer
    public void receiveTask(tasks task) {
        if (task != null) {
            receiveTask = true;
            assignedTasks.add(task);  // Add task to assigned tasks list
            System.out.println("Developer " + getName() + " received the task: " + task.getTaskName());
            System.out.println("Task details: " + task.getDetails());
        } else {
            System.out.println("Task cannot be null.");
        }
    }

    // Mark a bug as fixed by the developer
    public void fixBug(bugs bug) {
        if (bug == null || !assignedBugs.contains(bug)) {
            System.out.println("Error: This bug is not assigned to " + getName() + ".");
            return;
        }

        System.out.println("Developer " + getName() + " is fixing the bug: " + bug.getBugName());
        bug.setStatus(false);  // Mark the bug as fixed (closed)
        fixedBugs.add(bug);  
        assignedBugs.remove(bug);  
        System.out.println("Bug fixed successfully!");
    }

    
    public static List<Developer> getAllDevelopers() {
        return allDevelopers;  
    }

    
    public ArrayList<bugs> getFixedBugs() {
        return fixedBugs;  
    }

    
    public ArrayList<tasks> getAssignedTasks() {
        return assignedTasks;  
    }

    public int getCompletedBugsCount() {
        return fixedBugs.size();  
    }

    public ArrayList<bugs> getAssignedBugs() {
        return assignedBugs;  
    }
}
