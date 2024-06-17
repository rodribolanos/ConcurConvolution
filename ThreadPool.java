
// Implementación del ThreadPool
public class ThreadPool  {

    private final BufferN buffer;
    private final FilterWorker[] filterWorkers;
    WorkersCounter workersCounter;
    // Constructor para inicializar el buffer y los threads Worker
    public ThreadPool(int bufferSize, int cant_workers, WorkersCounter workersCounter) {
        buffer = new BufferN(bufferSize);
        filterWorkers = new FilterWorker[cant_workers];
        this.workersCounter = workersCounter;

        for(int i=0; i<cant_workers; i++) {
            filterWorkers[i] = new FilterWorker(buffer, workersCounter);
            filterWorkers[i].start();
        }
    }

    synchronized void launch(Runnable task) throws InterruptedException {
        buffer.write(task);
    }

    public void stop() throws InterruptedException {
        for (int i = 0; i < filterWorkers.length; i++) {
            launch(new PoisonPill());
        }
    }
}