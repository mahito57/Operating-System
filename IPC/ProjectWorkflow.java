package IPC;

import java.util.Random;
import java.util.concurrent.CountDownLatch;


public class ProjectWorkflow {
    private int numStudents;
    private int groupSize;
    private int numGroups;
    private PrintingStation[] printingStations;
    private BindingStation[] bindingStations;
    private EntryBook entryBook;
    private CountDownLatch[] groupLatches;
    private int printingtime;
    private int bindingtime;
    private int bookusetime;
    private long startTime;
//    private List<Integer>[] printingStationWaitLists;

    public ProjectWorkflow(int numStudents, int groupSize, int x, int y, int z) {
        this.numStudents = numStudents;
        this.groupSize = groupSize;
        this.numGroups = (numStudents + groupSize - 1) / groupSize;
        printingtime = x;
        bindingtime = y;
        bookusetime = z;
        initializePrintingStations();
        initializeBindingStations();
        entryBook = new EntryBook(bookusetime);
        initializeGroupLatches();
        startTime = System.currentTimeMillis();
//        printingStationWaitLists = new ArrayList[4];
//        for (int i = 0; i < 4; i++) {
//            printingStationWaitLists[i] = new ArrayList<>();
//        }
    }



    private void initializePrintingStations() {
        printingStations = new PrintingStation[4];

        for (int i = 0; i < 4; i++) {
            printingStations[i] = new PrintingStation(i + 1, groupSize, printingtime);
        }
    }

    private void initializeBindingStations() {
        bindingStations = new BindingStation[2];
        for (int i = 0; i < 2; i++) {
            bindingStations[i] = new BindingStation(i + 1, bindingtime);
        }
    }

    private void initializeGroupLatches() {
        groupLatches = new CountDownLatch[numGroups];
        for (int i = 0; i < numGroups; i++) {
            groupLatches[i] = new CountDownLatch(groupSize);
        }
    }

//    public void addToPrintingStationWaitList(int stationID, int studentID) {
//        printingStationWaitLists[stationID - 1].add(studentID);
//    }

    public int calculateGroupID(int studentID) {
        return (studentID - 1) / groupSize + 1;
    }

    public void runWorkflow() {
        for (int studentID = 1; studentID <= numStudents; studentID++) {
            int groupID = calculateGroupID(studentID);
            simulateStudent(studentID, groupID);
        }
    }

    private int elapsedtime(){
        long x = System.currentTimeMillis()-startTime;
        return (int) (x/100);
    }

    public void simulateStudent(int studentID, int groupID) {
        try {

            double meanArrivalRate = 10; // Set the mean arrival rate as needed
            Random random = new Random();

            // Generate random arrival time using Poisson distribution
            int arrivalTime = poissonRandom(meanArrivalRate, random);

            Thread.sleep(arrivalTime);
            System.out.println("Student " + studentID + " has arrived at the print station at time "+elapsedtime()+"\n");

            //All students will print first
            int stationID = (studentID % 4) + 1; // StationID for printer (studentID mod 4 + 1)
            printingStations[stationID - 1].useStation(studentID, groupLatches[groupID-1]);
            System.out.println("Student with ID " + studentID + " has finished printing at the station PS" + stationID + " at time "+ elapsedtime() + "\n");

            // Check if this student is the leader of the group
            if (studentID % groupSize == 0) {
                // Wait for all students in the group to finish printing
                groupLatches[groupID - 1].await();
                int bindingStationID = (int) (Math.random() * 2) + 1; // Random number between 1 and 2
                bindingStations[bindingStationID - 1].bindReport(groupID);
                System.out.println("Group " + groupID + " has finished binding the report at Binding Station BS" + bindingStationID + " at time "+ elapsedtime() + "\n");
                entryBook.writeEntry(groupID);
                System.out.println("Group " + groupID + " has written in the entry book at time "+ elapsedtime() + "\n");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void simulateStaff(int staffID) {

        while (true){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // Generate a random chance (between 0 and 1) for the staff member to read from the entry book
            double chanceToRead = Math.random();
            // If the chance is greater than or equal to 0.5, the staff member will read
            if (chanceToRead >= 0.75) {
                entryBook.readEntry();
                System.out.println("Staff " + staffID + " has started reading the entry book at time " + elapsedtime()+ " No. of submission = " + entryBook.getNumSubmissions()+ "\n");
                if(entryBook.numSubmissions == numGroups) break;
            }
        }
    }

    public static int poissonRandom(double mean, Random random) {
        double L = Math.exp(-mean);
        int k = 0;
        double p = 1.0;

        do {
            k+=50;
            p *= random.nextDouble();
        } while (p > L);

        return k - 1;
    }
}
