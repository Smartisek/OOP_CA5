package BusinessObjects;
import DTOs.CarClass;
import DAOs.JsonConverter;
import DAOs.MySqlCarDao;
import DAOs.CarDaoInterface;
import Exception.DaoException;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Main Author: Dominik Domalip
 */
public class ClientMenu {
    public static void main(String[] args) {
        ClientMenu clientMenu = new ClientMenu();
        clientMenu.start();
    }

    public void start(){
        // Json class converter to get car class from json later on
        JsonConverter JsonConverter = new JsonConverter();
        CarDaoInterface IUserDao = new MySqlCarDao();

        /**
         * Main Author: Dominik Domalip
         */
        // try to connect to server with port 7777
        try ( Socket socket = new Socket("localhost", 7777);
              // printWritter send data to server
              PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
              // bufferedReader to receive data from server
              BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                ){
            System.out.println("Client Menu message: The Client Menu is running and has connected to the server.");
            Scanner consoleInput = new Scanner(System.in);
            System.out.println("Valid commands: " + "\n" + "displayEntity ID" + "\n" + "displayAll" + "\n" + "add model brand colour production_year price" +
                    "\n" + "delete ID" + "\n" + "quit");
           String userInput = "";

            while(!userInput.startsWith("quit")){
                System.out.println("Enter command: ");
                userInput = consoleInput.nextLine();
                out.println(userInput);

                if(userInput.startsWith("displayEntity")){
                    // receive output from server in Json format
                    String carJson = in.readLine();
                    // turn json format into car class and then print into console
                    CarClass car = JsonConverter.fromJson(carJson);
                    System.out.println("Client Menu message: Response from server: \"" + car + "\n" );
                } else if(userInput.startsWith("displayAll")){
                    String cars = in.readLine();
                    System.out.println("Client Menu message: Received from server: " +"\n" + cars + "\n");
                    List<CarClass> allCars = JsonConverter.JsonToCarList(cars);
                    for(CarClass car : allCars){
                        System.out.println(car);
                    }
                } else if(userInput.startsWith("add")){
//                split input into parts so we can find parts for input
                    String[] parts = userInput.split(" ");
//                parsing string data into integer needed
                    int production_year = Integer.parseInt(parts[4]);
                    int price = Integer.parseInt(parts[5]);
//                creating an instance for the requested insert
                    CarClass insertRequest = new CarClass(0, parts[1], parts[2], parts[3], production_year, price);
                    String jsonRequest = JsonConverter.carObjectToJson(insertRequest);
                    System.out.println("Client Menu message: Sending out " + jsonRequest);
//                send the request to the server
                    out.println(jsonRequest);
                    System.out.println("Client Menu message: Receiving response from server...");
//                get response from the server with entity or error message
                    String newCar = in.readLine();
                    System.out.println("Client Menu message: New entity was added into database. New entity: " + "\n" + newCar);
                } else if(userInput.startsWith("delete")){
//                splitting input to be able to pass id in
                    String[] parts = userInput.split(" ");
                    int id = Integer.parseInt(parts[1]);
//                using dao function find car by id so we can put it into json format, this could be just done with request as int id but since the specification
//                says to send to server request in Json, I am using this function to get car with that id and parsing it into json
                    CarClass request = IUserDao.findCarById(id);
//                carclass to json
                    String requestJson = JsonConverter.carObjectToJson(request);
                    System.out.println("Client Menu message: Sending request to server to delete: " + requestJson);
//                send request to sever
                    out.println(requestJson);
                    String income = in.readLine();
                    System.out.println(income + requestJson);
                }else if(userInput.startsWith("quit")){
                    out.println("Client menu is being terminated.");
                    System.out.println("Client Menu message: This client is being terminated.");
//                    after break statement the while loop exits and that ends the clients session, after try statement ensures that socket, printWritter and buffer are closed and disconnects from server
                    break;
                }
            }
        } catch (IOException e){
            System.out.println("Client message: IOException: " + e);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Exiting client, but server may still be running");
    }

}
