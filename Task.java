import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

class concurrente {

    public static void main(String[] args) {
        File carpeta = new File("./assets");
        if (carpeta.exists() && carpeta.isDirectory()) {
            File[] archivos = carpeta.listFiles();
        for (File imagen : archivos) {
            Task tarea = new Task(imagen);
            tarea.run();    
            }
        }
    }
}

public class Task implements Runnable {
    private final File inputFile;

    private static final float[][] kernel = {
        {0, -1, 0},
        {-1, 4, -1},
        {0, -1, 0}
    };

    public Task(File inputFile) {
        this.inputFile = inputFile;
    }

    @Override
    public void run() {
        try {
            BufferedImage image = ImageIO.read(inputFile);
            BufferedImage resultImage = applyLaplacianFilter(image);
            File carpetaSalida = new File("./carpeta_salida");
            if (!carpetaSalida.exists()) {
                carpetaSalida.mkdirs(); // Crea la carpeta si no existe
            }
            File outputFile = new File(carpetaSalida, "Salida_" + inputFile.getName());
            ImageIO.write(resultImage, "jpg", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private BufferedImage applyLaplacianFilter(BufferedImage image) {
        WritableRaster raster = image.getRaster();
        int ancho = raster.getWidth();
        int alto = raster.getHeight();
        int canales = raster.getNumBands();
        WritableRaster raster2 = raster.createCompatibleWritableRaster(ancho, alto);

        for (int y = 1; y < alto - 1; y++) {
            for (int x = 1; x < ancho - 1; x++) {
                for (int k = 0; k < canales; k++) {
                    int pixelValue = 0;
                    for (int ky = -1; ky <= 1; ky++) {
                        for (int kx = -1; kx <= 1; kx++) {
                            int sample = raster.getSample(x + kx, y + ky, k);
                            pixelValue += sample * kernel[ky + 1][kx + 1];
                        }
                    }
                    pixelValue = Math.min(Math.max(pixelValue, 0), 128);
                    raster2.setSample(x, y, k, pixelValue);
                }
            }
        }

        BufferedImage outputImage = new BufferedImage(image.getColorModel(), raster2, image.isAlphaPremultiplied(), null);
        return outputImage;
    }
}

