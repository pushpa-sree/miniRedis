
import client.ClientHandler;
import datastore.DataStore;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ClientHandlerTest {

    public static void main(String[] args) throws Exception {
        DataStore store = new DataStore();

        // Create a temporary server on an available port.
        try (ServerSocket server = new ServerSocket(0)) {
            ExecutorService executor = Executors.newSingleThreadExecutor();

            try {
                // Start the handler when a client connects.
                Future<?> handlerTask = executor.submit(() -> {
                    try {
                        Socket acceptedSocket = server.accept();
                        new ClientHandler(acceptedSocket, store).run();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });

                // Connect our test client to the temporary server.
                try (Socket client = new Socket(
                        "localhost", server.getLocalPort());
                     BufferedReader input = new BufferedReader(
                        new InputStreamReader(client.getInputStream()));
                     PrintWriter output = new PrintWriter(
                        client.getOutputStream(), true)) {

                    // Test 1: PING
                    output.println("PING");
                    assert "PONG".equals(input.readLine())
                            : "PING should return PONG";

                    // Test 2: SET
                    output.println("SET name Alice");
                    assert "OK".equals(input.readLine())
                            : "SET should return OK";

                    // Test 3: GET
                    output.println("GET name");
                    assert "Alice".equals(input.readLine())
                            : "GET should return the stored value";

                    // Test 4: INCR
                    output.println("INCR score");
                    assert "1".equals(input.readLine())
                            : "First INCR should return 1";

                    // Test 5: INCR again
                    output.println("INCR score");
                    assert "2".equals(input.readLine())
                            : "Second INCR should return 2";

                    // Test 6: Invalid command
                    output.println("HELLO");
                    assert "ERR invalid command".equals(input.readLine())
                            : "Invalid commands should return an error";

                    // Test 7: Lowercase command
                    output.println("ping");
                    assert "PONG".equals(input.readLine())
                            : "Lowercase ping should return PONG";

                    System.out.println(
                            "All ClientHandler integration tests passed!");
                }

                // Wait for the handler to finish after client disconnects.
                handlerTask.get();

            } finally {
                executor.shutdownNow();
            }
        }
    }
}
