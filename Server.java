import java.net.*;
import java.util.ArrayList;
import java.io.*; 

public class Server {
    ArrayList<ServerManagementThread> ThreadList;
    private static final Integer serverPort = 6666;

    public static void main(String[] args) throws IOException {

        try{
            ServerSocket serverSocket = new ServerSocket(serverPort);
            Server server = new Server();
            System.out.println("Servidor iniciado, esperando novas conexões!!");
            try{
                while(true) {
                    Socket socket = serverSocket.accept();
                    server.addThread(new ServerManagementThread(socket, server));
                }
            }catch (IOException e) { 
                System.err.println("Falha na conexão");
                System.err.println(e.getMessage());
                System.exit(1); 
            } 
        }
        catch (IOException e) {
            System.err.println("não é possivel escutar a porta: 6666."); 
            System.exit(1); 
        } 
    }

    public Server(){
        this.ThreadList = new  ArrayList<ServerManagementThread>();
    }

    public void addThread(ServerManagementThread thread){
        this.ThreadList.add(thread);
    }

    public void removeThread(ServerManagementThread thread){
        this.ThreadList.remove(thread);
    }

    public void SpreadMessages(String message, ServerManagementThread source){
        for(ServerManagementThread thread : this.ThreadList){
            if(thread != source){
                thread.SendMessage(message);
            }
        }
    }

    public String ListUsers() {
        String userString = "";
        userString += "Usuarios conectados: ";
        for (ServerManagementThread thread : this.ThreadList) {
            userString += thread.getUserName() + ", ";
        }
        userString = userString.substring(0, userString.length()-2);
        System.out.println(userString);
        return userString;
    }

}