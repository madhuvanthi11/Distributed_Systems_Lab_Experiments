import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(5000);
            System.out.println("Server waiting for connection...");

            Socket socket = server.accept();
            System.out.println("Client connected");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader userInput = new BufferedReader(
                    new InputStreamReader(System.in));

            while (true) {
                String clientMsg = in.readLine();
                System.out.println("Client: " + clientMsg);

                System.out.print("Server: ");
                String reply = userInput.readLine();
                out.println(reply);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}