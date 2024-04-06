package BusinessObjects;
import DAOs.CarDaoInterface;
import DAOs.JsonConverter;
import DAOs.MySqlCarDao;
import Exception.DaoException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    final int SERVER_PORT_NUMBER = 7777;

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

    public void start(){
        CarDaoInterface IUserDao = new MySqlCarDao();
        JsonConverter JsonConverter = new JsonConverter();
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT_NUMBER); ){
            while (true){
                try (Socket clientSocket = serverSocket.accept();
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                ){
                    System.out.println("Server Message: A Client Menu has connected.");
//                    Wait for input from the client menu and then read it
                    String request = in.readLine();

                    System.out.println("Server Message: Received from Client Menu: \"" + request + "\n");

                    if(request.startsWith("display")){
                        String[] parts = request.split(" ");
                        int id = Integer.parseInt(parts[1]);
                        IUserDao.findCarById(id);
                    }
                } catch (DaoException e) {
                    throw new RuntimeException(e);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
