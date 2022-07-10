import java.net.*; 
import java.io.*; 

public class ClientManager {
    private static final String serverURL = "localhost";
    private static final Integer serverPort = 6666;

    public static void main(String[] args) throws IOException {
        try {
            Socket serverSocket = new Socket(serverURL, serverPort);

            String userName = System.console().readLine("Digite um apelido: ");

            new ReceiveMessageThread(serverSocket);
            new SendMessageThread(serverSocket, userName);
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
