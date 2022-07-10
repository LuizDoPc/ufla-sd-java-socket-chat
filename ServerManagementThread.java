import java.net.*;
import java.io.*;

public class ServerManagementThread extends Thread{
    protected Socket socket;
    protected PrintWriter serverOutput;
    protected Server server;
    protected String userName;

    public ServerManagementThread (Socket socket, Server server) {
        this.server = server;
        this.socket = socket;
        this.userName = "";
        start();
    }

    public void run() {
        try {
            serverOutput = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader serverInput = new BufferedReader(new InputStreamReader( socket.getInputStream()));

            this.userName = serverInput.readLine();
            System.out.println("Servidor: " + this.userName + " acaba de entrar na sala");
            this.server.SpreadMessages("Servidor: " + this.userName + " acaba de entrar na sala", this);
            SendMessage(this.server.ListUsers());

            while (true) {
                String inputLine = serverInput.readLine();
                this.server.SpreadMessages("-" + this.userName + ": "+ inputLine, this);
                if (inputLine.equals(":q")) {
                    System.out.println("Servidor: " + this.userName + " acaba de entrar na sala");
                    break;
                }
            }
            serverInput.close();
            this.serverOutput.close();
            this.socket.close();
            server.removeThread(this);
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    public void SendMessage(String message) {
        this.serverOutput.println(message);
    }

    public String getUserName() {
        return (this.userName);
    }
}
