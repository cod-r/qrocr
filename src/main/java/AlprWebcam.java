import com.github.sarxos.webcam.Webcam;
import com.openalpr.jni.*;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class AlprWebcam {

    public void readNumberPlate(){
        Alpr alpr = new Alpr("eu", "/etc/openalpr/openalpr.conf",
                "/usr/local/src/openalpr/tesseract-3.05.02/openalpr/runtime_data/");

        alpr.setTopN(5);

        // Set pattern to Romania
        alpr.setDefaultRegion("ro");

        AlprResults results = null;
        try {
            results = alpr.recognize(readWebCam());

        } catch (AlprException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.format("  %-15s%-8s\n", "Plate Number", "Confidence");
        for (
                AlprPlateResult result : results.getPlates()) {
            for (AlprPlate plate : result.getTopNPlates()) {
                if (plate.isMatchesTemplate())
                    System.out.print("  * ");
                else
                    System.out.print("  - ");
                System.out.format("%-15s%-8f\n", plate.getCharacters(), plate.getOverallConfidence());
            }
        }

        // Make sure to call this to release memory
        alpr.unload();
    }

    private String readWebCam() throws IOException {

        System.out.println("Press 'x' to scan the number plate");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        if ("x".equals(input)) {
            // get camera image
            Webcam webcam = Webcam.getDefault();
            webcam.open();
            File webCamPic = new File("./src/main/resources/number_plate.png");
            ImageIO.write(webcam.getImage(), "PNG", webCamPic);

            return webCamPic.getPath();
        } else {
            throw new IOException();
        }

    }
}