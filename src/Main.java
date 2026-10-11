
import client.ClientHandler;
import datastore.DataStore;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(6379);

        System.out.println("MiniRedis server started on port 6379...");

        DataStore datastore = new DataStore();

        while (true) {
            Socket clientSocket = serverSocket.accept();

            System.out.println("Client connected!");

            ClientHandler handler =
                    new ClientHandler(clientSocket, datastore);

            Thread clientThread = new Thread(handler);
            clientThread.start();
        }
    }
}