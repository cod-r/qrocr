import com.github.sarxos.webcam.Webcam;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class QrCodeReader {

    public void ocr() throws IOException {

        System.out.println("Press 'x' to scan the qr code");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        if ("x".equals(input)) {
            // camera image
            Webcam webcam = Webcam.getDefault();
            webcam.open();
            ImageIO.write(webcam.getImage(), "PNG", new File("./src/main/resources/qr.png"));

            try {
                File file = new File("./src/main/resources/qr.png");
                String decodedText = decodeQRCode(file);
                if (decodedText == null) {
                    System.out.println("No QR Code found in the image");
                } else {
                    System.out.println("Decoded text = " + decodedText);
                }
            } catch (IOException e) {
                System.out.println("Could not decode QR Code, IOException :: " + e.getMessage());
            }
        }
    }

    private String decodeQRCode(File qrCodeImage) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(qrCodeImage);
        LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

        try {
            Result result = new MultiFormatReader().decode(bitmap);
            return result.getText();
        } catch (NotFoundException e) {
            System.out.println("There is no QR code in the image");
            return null;
        }
    }
}
