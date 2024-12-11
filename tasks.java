
package bugtrackingsystem;

import java.util.Date;
import java.util.ArrayList;

public class tasks {
    private String taskName;
    private int taskNumber = 0;
    private String taskDescription;
    private Date taskDade;
    private double details;
    public static ArrayList<tasks> listTasks = new ArrayList<>();

    public tasks(String taskName, String taskDescription, double details, String name, int ID) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.details = details;
        this.taskNumber++; // Increment the task number for each new task
        this.taskDade = new Date(); // Set task date as the current date
        listTasks.add(this); // Add the task to the global list of tasks
    }

    // Getters for task properties
    public String getTaskName() {
        return taskName;
    }

    public int getTaskNumber() {
        return taskNumber;
    }

    public Date getTaskDade() {
        return taskDade;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public double getDetails() {
        return details;
    }
}
