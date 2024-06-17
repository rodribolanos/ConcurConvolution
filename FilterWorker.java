class FilterWorker extends Thread {

    private BufferN buffer;

    public FilterWorker(BufferN buffer) {
        this.buffer = buffer;
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
            System.out.println("Worker detenido: " + e.getMessage());
        }
    }
}