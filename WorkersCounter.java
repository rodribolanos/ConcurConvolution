public class WorkersCounter {
    private int workers;

    public WorkersCounter(int workers) {
        this.workers = workers;
    }
    public synchronized void finish() {
        while(workers != 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void decrease() {
        workers--;
        if (workers == 0 ) {
            notify();
        }
    }
}
