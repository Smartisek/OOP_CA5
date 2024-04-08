package BusinessObjects;
import DTOs.CarClass;
import DAOs.JsonConverter;
import DTOs.CarClass;
import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ClientMenu {
    public static void main(String[] args) {
        ClientMenu clientMenu = new ClientMenu();
        clientMenu.start();
    }

    public void start(){
        // Json class converter to get car class from json later on
        JsonConverter JsonConverter = new JsonConverter();

        // try to connect to server with port 7777
        try ( Socket socket = new Socket("localhost", 7777);
              // printWritter send data to server
              PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
              // bufferedReader to receive data from server
              BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                ){
            System.out.println("Client Menu message: The Client Menu is running and has connected to the server.");
            Scanner consoleInput = new Scanner(System.in);
            System.out.println("Valid commands: " + "\n" + "display ID" + "\n" + "displayAll");
            System.out.println("Enter command: ");
            String userInput = consoleInput.nextLine();
            out.println(userInput);

            if(userInput.startsWith("displayEntity")){
                // receive output from server in Json format
                String carJson = in.readLine();
                // turn json format into car class and then print into console
                CarClass car = JsonConverter.fromJson(carJson);
                System.out.println("Client Menu message: Response from server: \"" + car + "\n" );
            } else if(userInput.startsWith("displayAll")){
                String cars = in.readLine();
//                List<CarClass> carList = Arrays.asList(new Gson().fromJson(cars, CarClass.class));
                System.out.println("Client Menu message: Received from server: " +"\n" + cars + "\n");
                List<CarClass> allCars = JsonConverter.JsonToCarList(cars);
                for(CarClass car : allCars){
                    System.out.println(car);
                }
            }
        } catch (IOException e){
            System.out.println("Client message: IOException: " + e);
        }
        System.out.println("Exiting client, but server may still be running");
    }

}
