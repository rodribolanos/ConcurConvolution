class FilterWorker extends Thread {

    private BufferN buffer;
    private WorkersCounter workersCounter;

    public FilterWorker(BufferN buffer, WorkersCounter workersCounter) {
        this.buffer = buffer;
        this.workersCounter = workersCounter;
    }

    public void run() {
        try {
            while (true) {
                Runnable task = buffer.read();
                task.run();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (PoisonException e) {
            workersCounter.decrease();
            System.out.println("Worker detenido: " + e.getMessage());
        }
    }
}