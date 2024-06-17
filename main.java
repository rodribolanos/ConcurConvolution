import java.io.File;

class            concurrente {

    public static void main(String[] args) throws InterruptedException {
        ThreadPool threadPool = new ThreadPool(10, 4);
        File carpeta = new File("./assets");
        if (carpeta.exists() && carpeta.isDirectory()) {
            File[] archivos = carpeta.listFiles();
            for (File imagen : archivos) {
                threadPool.launch(new FilterTask(imagen));
            }
            threadPool.stop();
        }
    }
}
