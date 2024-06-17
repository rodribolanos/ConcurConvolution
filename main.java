import java.io.File;

class            concurrente {

    public static void main(String[] args) throws InterruptedException {
        int cantidadWorkers = 16;
        WorkersCounter workersCounter = new WorkersCounter(cantidadWorkers);
        ThreadPool threadPool = new ThreadPool(32, cantidadWorkers, workersCounter);
        File carpeta = new File("./assets");
        File[] archivos = carpeta.listFiles();
        long startTime = System.currentTimeMillis();
        for (File imagen : archivos) {
            threadPool.launch(new FilterTask(imagen));
        }
        threadPool.stop();
        workersCounter.finish();
        System.out.println("Tiempo total de ejecución: " + (System.currentTimeMillis() - startTime) + "ms");
    }
}
