package IPC;

import java.util.concurrent.Semaphore;

class BindingStation {
    private int stationID;
    private Semaphore stationSemaphore;
    private int bindTime;

    public BindingStation(int stationID , int bindTime) {
        this.stationID = stationID;
        this.stationSemaphore = new Semaphore(1); // Initialize the semaphore with 1 permit (available station)
        this.bindTime = bindTime;
    }

    public void bindReport(int groupID) {
        try {
            // Acquire the semaphore for the binding station (if available)
            stationSemaphore.acquire();

            // Simulate the binding process
//            System.out.println("Group " + groupID + " is binding the report at Binding Station BS" + stationID);
            Thread.sleep(bindTime*100);

            // Release the semaphore (station becomes available)
            stationSemaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
