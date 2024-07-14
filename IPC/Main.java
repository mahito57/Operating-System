package IPC;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int numStudents = sc.nextInt();
        int groupSize = sc.nextInt();
        int printingtime = sc.nextInt();
        int bindingtime = sc.nextInt();
        int bookusetime = sc.nextInt();

        ProjectWorkflow projectWorkflow = new ProjectWorkflow(numStudents, groupSize, printingtime, bindingtime, bookusetime);

        // Create and start staff threads
        Thread[] staffThreads = new Thread[2];
        for (int staffID = 1; staffID <= 2; staffID++) {
            Staff staff = new Staff(staffID, projectWorkflow);
            staffThreads[staffID - 1] = new Thread(staff);
            staffThreads[staffID - 1].start();
        }
        // Create and start student threads
        Thread[] studentThreads = new Thread[numStudents];
        for (int studentID = 1; studentID <= numStudents; studentID++) {
            int groupID = projectWorkflow.calculateGroupID(studentID);
            Student student = new Student(studentID, groupID, projectWorkflow);
            studentThreads[studentID - 1] = new Thread(student);
            studentThreads[studentID - 1].start();
        }

        // Wait for all student threads to finish
        for (Thread studentThread : studentThreads) {
            try {
                studentThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Wait for all staff threads to finish
        for (Thread staffThread : staffThreads) {
            try {
                staffThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


class Student implements Runnable {
    private int studentID;
    private int groupID;
    private ProjectWorkflow projectWorkflow;

    public Student(int studentID, int groupID, ProjectWorkflow projectWorkflow) {
        this.studentID = studentID;
        this.groupID = groupID;
        this.projectWorkflow = projectWorkflow;
    }

    @Override
    public void run() {
        projectWorkflow.simulateStudent(studentID, groupID);
    }
}

// class representing a staff member
class Staff implements Runnable {
    private int staffID;
    private ProjectWorkflow projectWorkflow;

    public Staff(int staffID, ProjectWorkflow projectWorkflow) {
        this.staffID = staffID;
        this.projectWorkflow = projectWorkflow;
    }

    @Override
    public void run() {
        projectWorkflow.simulateStaff(staffID);
    }
}
