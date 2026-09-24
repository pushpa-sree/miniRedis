import java.io.*; 
import java.net.*;
public class Main{
    public static void main(String[] args)  throws IOException{
        ServerSocket serverSocket = new ServerSocket(6379);
        System.out.println("Server started....");
        Socket clientSocket = serverSocket.accept();
        System.out.println("Clinet connected!");
        BufferedReader input = new BufferedReader(
            new InputStreamReader(clientSocket.getInputStream())
        );
        PrintWriter output = new PrintWriter(clientSocket.getOutputStream() , true);
        String message = input.readLine();
        System.out.println("Receieved :" + message);
        output.println("PONG");

    }
}