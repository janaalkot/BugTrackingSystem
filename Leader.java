/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bugtrackingsystem;

import java.util.ArrayList;

public class Leader extends person {
    private ArrayList<tasks> taskList;

    public Leader(String name, int ID) {
        super(name, ID);
        taskList = new ArrayList<>();
    }

    public tasks createTask(String taskName, String description, double details, Developer developer) {
        // Create the task using Leader's name and ID, not hardcoded values
        tasks task = new tasks(taskName, description, details, this.getName(), this.getID());
        
        // Add the task to the global list of tasks
        tasks.listTasks.add(task);
        
        // Print confirmation of task creation
        System.out.println("Task '" + taskName + "' has been created and assigned to " + developer.getName());
        
        // Assign the task to the developer
        developer.receiveTask(task);
        
        return task;
    }
}

