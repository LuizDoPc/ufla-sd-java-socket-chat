import java.net.*;
import java.io.*;

public class SendMessageThread extends Thread {
    private Socket socket;
    private String userName;

    public SendMessageThread (Socket socket, String userName) {
        this.socket = socket;
        this.userName = userName;
        start();
    }

    public void run() {
        try {
            PrintWriter serverOutput = new PrintWriter(socket.getOutputStream(), true);

            if(serverOutput == null) {
                throw new Error("Failed to connect.");
            }

            serverOutput.println(userName);

            while (true) {
                String inputLine = System.console().readLine();

                serverOutput.println(inputLine);

                if (inputLine.equals(":q")) {
                   break;
                }
            } 

            socket.close();
            serverOutput.close();
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
