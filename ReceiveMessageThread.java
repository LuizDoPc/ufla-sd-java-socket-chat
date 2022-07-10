import java.net.*;
import java.io.*;

public class ReceiveMessageThread extends Thread {
    private Socket socket;

    public ReceiveMessageThread  (Socket socket) {
        this.socket = socket;
        start();
    }

    public void run() {
        try {
            BufferedReader serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            if(serverInput == null) {
                throw new Error("Failed to connect.");
            }

            // vi also does not teach you how to leave.
            // System.out.println("Digite \":q\" para sair.");

            while (true) {
                String currentMessage  = serverInput.readLine();
                
                System.out.println(currentMessage);

                if (currentMessage.equals(":q")) { 
                    break; 
                }
            }

            socket.close();
            serverInput.close();
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
