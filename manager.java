/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bugtrackingsystem;

/**
 *
 * @author Jana
 */
import java.util.Iterator;

public class manager extends person{

    manager(String name , int ID){
        super(name,ID);
    }
    public void check_performance(Developer developer, tester tester) {
        int developerBugsFixed = 0;
        int testerBugsReported = 0;

        for (bugs bug : bugs.listBug) {
            if (bug.getStatus() == false && bug.getProjectName().equalsIgnoreCase(developer.getName())) {
                developerBugsFixed++;
            }
        }


        for (bugs bug : bugs.listBug) {
            if (bug.getStatus() == true && bug.getProjectName().equalsIgnoreCase(tester.getName())) {
                testerBugsReported++;
            }
        }

        System.out.println("Developer " + developer.getName() + " has fixed " + developerBugsFixed + " bugs.");
        System.out.println("Tester " + tester.getName() + " has reported " + testerBugsReported + " bugs.");
    }

    public void openBug () {
        Iterator<bugs> iterator = bugs.listBug.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next().toString());
        }
    }


}
