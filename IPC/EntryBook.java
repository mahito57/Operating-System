package IPC;

import java.util.concurrent.Semaphore;

class EntryBook {
    private Semaphore readerSemaphore;
    private Semaphore writerSemaphore;
    private int numReaders;
    private int bookUseTime;

    public int getNumSubmissions() {
        return numSubmissions;
    }

    int numSubmissions;

    public EntryBook(int bookUseTime) {
        this.readerSemaphore = new Semaphore(1); // Only one thread can access readers at a time
        this.writerSemaphore = new Semaphore(1); // Only one thread can access writers at a time
        this.numReaders = 0;
        this.bookUseTime = bookUseTime;
        this.numSubmissions = 0;
    }

    public void writeEntry(int groupID) {
        try {
            writerSemaphore.acquire(); // Acquire the writer semaphore

            // Simulate writing in the entry book
            Thread.sleep(bookUseTime * 100);
            numSubmissions++;

            writerSemaphore.release(); // Release the writer semaphore
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void readEntry() {
        try {
            readerSemaphore.acquire(); // Acquire the reader semaphore

            // Increase the count of active readers
            numReaders++;

            if (numReaders == 1) {
                // Acquire the writer semaphore if it's the first reader to block writers
                writerSemaphore.acquire();
            }

            readerSemaphore.release(); // Release the reader semaphore

            // Simulate reading from the entry book
            Thread.sleep(bookUseTime * 100);

            readerSemaphore.acquire(); // Acquire the reader semaphore

            // Decrease the count of active readers
            numReaders--;

            if (numReaders == 0) {
                // Release the writer semaphore if no readers left
                writerSemaphore.release();
            }

            readerSemaphore.release(); // Release the reader semaphore
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
