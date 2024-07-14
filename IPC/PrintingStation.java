package IPC;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

class PrintingStation {
    private int stationID;
    private Semaphore stationSemaphore;
    private int numStudentsInGroup;
    private int printTime;

    public PrintingStation(int stationID, int numStudentsInGroup, int printTime) {
        this.stationID = stationID;
        this.stationSemaphore = new Semaphore(1); // Initialize the semaphore with 1 permit (available station)
        this.numStudentsInGroup = numStudentsInGroup;
        this.printTime = printTime;
    }

    public void useStation(int studentID, CountDownLatch grouplatch) {
        try {
            // Acquire the semaphore to use the station
            stationSemaphore.acquire();

//            System.out.println("Student with ID " + studentID + " is using the station PS" + stationID);

            // Simulate the use of the station
            Thread.sleep(printTime*100);

            // Release the semaphore (station becomes available)
            stationSemaphore.release();
            grouplatch.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setNumStudentsInGroup(int numStudentsInGroup) {
        this.numStudentsInGroup = numStudentsInGroup;
    }
}
