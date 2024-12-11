/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bugtrackingsystem;

/**
 *
 * @author Jana
 */
public class person {
    private String name;
    private int ID;
    private String password;
    private String role;

    public person() {}

    public person(String name, int ID) {
        this.name = name;
        this.ID = ID;
    }

    // Getter and setter methods for name and ID
    public String getName() {
        return name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and setter methods for password and role
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
