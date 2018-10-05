import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        AlprWebcam alprWebcam = new AlprWebcam();
        alprWebcam.readNumberPlate();

//        QrCodeReader qrCodeReader = new QrCodeReader();
//        try {
//            qrCodeReader.ocr();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}