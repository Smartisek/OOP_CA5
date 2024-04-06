package BusinessObjects;
import DTOs.CarClass;
import DAOs.JsonConverter;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientMenu {
    public static void main(String[] args) {

    }

    public void start(){
        JsonConverter JsonConverter = new JsonConverter();
        try ( Socket socket = new Socket("localhost", 7777);
              PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
              BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                ){
            System.out.println("Client Menu message: The Client Menu is running and has connected to the server.");
            Scanner consoleInput = new Scanner(System.in);
            System.out.println("Valid commands: \"display ID");
            System.out.println("Enter command: ");
            String userInput = consoleInput.nextLine();
            out.println(userInput);

            if(userInput.startsWith("display")){
                String carJson = in.readLine();
                CarClass car = JsonConverter.
            }
        } catch (IOException e){
            System.out.println("Client message: IOException: " + e);
        }
    }

}
