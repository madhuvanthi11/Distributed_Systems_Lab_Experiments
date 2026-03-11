import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("SERVER_IP", 5000);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader userInput = new BufferedReader(
                    new InputStreamReader(System.in));

            while (true) {
                System.out.print("Client: ");
                String msg = userInput.readLine();
                out.println(msg);

                String reply = in.readLine();
                System.out.println("Server: " + reply);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}