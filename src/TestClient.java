
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TestClient {
    public static void main(String[] args) {
        try (
            Socket socket = new Socket("localhost", 6379);
            BufferedReader serverReader = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
            PrintWriter serverWriter = new PrintWriter(
                socket.getOutputStream(), true);
            BufferedReader keyboard = new BufferedReader(
                new InputStreamReader(System.in))
        ) {
            System.out.println("Connected to MiniRedis!");
            System.out.println("Enter a command (type EXIT to quit):");

            String command;

            while ((command = keyboard.readLine()) != null) {
                if (command.equalsIgnoreCase("EXIT")) {
                    break;
                }

                serverWriter.println(command);

                String response = serverReader.readLine();
                System.out.println("Server: " + response);
            }

        } catch (Exception e) {
            System.out.println("Client error: " + e.getMessage());
        }
    }
}
