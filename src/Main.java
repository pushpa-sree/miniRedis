import java.io.*;
import java.net.*;
import java.util.concurrent.ConcurrentHashMap;

public class Main {

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(6379);

        System.out.println("MiniRedis server started on port 6379...");

        ConcurrentHashMap<String, String> datastore =
                new ConcurrentHashMap<>();

        while (true) {

            Socket clientSocket = serverSocket.accept();

            System.out.println("Client connected!");

            Thread clientThread = new Thread(() -> {

                try {

                    BufferedReader input =
                            new BufferedReader(
                                    new InputStreamReader(
                                            clientSocket.getInputStream()
                                    )
                            );

                    PrintWriter output =
                            new PrintWriter(
                                    clientSocket.getOutputStream(),
                                    true
                            );

                    String message;

                    while ((message = input.readLine()) != null) {
                         String[] parts = message.split(" ", 3);
                         String command = parts[0]; 
                        if (command.equals("PING")) {

                            output.println("PONG");

                        } else if (command.startsWith("SET")) {

                           if(parts.length< 3){
                              output.println("ERR Wrong number of arguments for SET");
                              continue; 
                           }

                            String key = parts[1];
                            String value = parts[2];

                            datastore.put(key, value);

                            output.println("OK");

                        } else if (command.startsWith("GET")) {

                            if(parts.length < 2){
                                output.println("ERR Wrong number of arguments for GET");
                                continue;
                            }

                            String value = datastore.get(parts[1]);

                            if (value != null) {
                                output.println(value);
                            } else {
                                output.println("nil");
                            }

                        } else {

                            output.println("ERR unknown command");
                        }
                    }

                    clientSocket.close();

                } catch (IOException e) {

                    System.out.println("Client disconnected.");
                }
            });

            clientThread.start();
        }
    }
}