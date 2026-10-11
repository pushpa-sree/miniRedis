
package client;

import command.Command;
import command.CommandExecutor;
import command.CommandParser;
import datastore.DataStore;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private final Socket clientSocket;
    private final CommandParser parser = new CommandParser();
    private final CommandExecutor executor;

    public ClientHandler(Socket clientSocket, DataStore datastore) {
        this.clientSocket = clientSocket;
        this.executor = new CommandExecutor(datastore);
    }

    @Override
    public void run() {
        try (
            Socket socket = clientSocket;
            BufferedReader input = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(
                socket.getOutputStream(), true)
        ) {
            String message;

            while ((message = input.readLine()) != null) {
                Command command = parser.parse(message);

                if (command == null) {
                    output.println("ERR invalid command");
                    continue;
                }

                String response = executor.execute(command);
                output.println(response);
            }

        } catch (IOException e) {
            System.out.println("Client disconnected.");
        }
    }
}
