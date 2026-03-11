import java.rmi.Naming;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileClient {

    public static void main(String[] args) {
        try {
            FileService service = (FileService) Naming.lookup("rmi://localhost/FileService");

            String filePath = "client_files/sample.txt";
            byte[] fileData = Files.readAllBytes(Paths.get(filePath));
            String fileName = Paths.get(filePath).getFileName().toString();

            service.sendFile(fileName, fileData);

            System.out.println("File sent successfully: " + fileName);

        } catch (Exception e) {
            System.out.println("Client error: " + e.getMessage());
        }
    }
}