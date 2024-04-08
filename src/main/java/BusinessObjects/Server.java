package BusinessObjects;
import DAOs.CarDaoInterface;
import DAOs.JsonConverter;
import DAOs.MySqlCarDao;
import DTOs.CarClass;
import Exception.DaoException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

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
                // create a connection, server accepts connection from client
                try (Socket clientSocket = serverSocket.accept();
//             // printWriter is used to send data to the client menu
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                // bufferReader is used to receive data from client menu
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                ){
//                    Message for when we successfully connect to client
                    System.out.println("Server Message: A Client Menu has connected.");
//                    This makes the program wait until we read a request (input) from client menu
                    String request = in.readLine();

                    System.out.println("Server Message: Received from Client Menu: \"" + request + "\n");
//  Logic for handling the request from client
                    if(request.startsWith("displayEntity")){
//                        put request into an array and split it by space
                        String[] parts = request.split(" ");
//                        second part of request will be id that gets passed into IUserDao function
                        int id = Integer.parseInt(parts[1]);
                        CarClass car = IUserDao.findCarById(id);
//                        convert output class into JSon
                        String carJson = JsonConverter.carObjectToJson(car);
                        out.println(carJson);
                    } else if(request.startsWith("displayAll")){
                        List<CarClass>allCars =IUserDao.findAllCars();
                        String carsJson = JsonConverter.carListToJson(allCars); //*********
                        System.out.println("Server Message: Sending to Client Menu: " + "\n" + carsJson + "\n");
                        out.println(carsJson);
                    } else if(request.startsWith("add")){
//                        get the request from client in json format
                        String jsonRequest = in.readLine();
                        System.out.println("Server message: Server receives " + jsonRequest);
//                        convert json request into car class object
                        CarClass carRequest = JsonConverter.fromJson(jsonRequest);
//                        calling onto insertCar function in DAO with our interface and passing in requested data from carRequest instance
                        CarClass newCar = IUserDao.insertCar(carRequest.getModel(), carRequest.getBrand(), carRequest.getColour(), carRequest.getProduction_year(), carRequest.getPrice());
//                        if newCar is not null meaning it was added so print message and send result to client
                        if(newCar != null){
                            System.out.println("Server message: Entity successfully added. Entity: " + "\n" + newCar);
                            String jsonNewCar = JsonConverter.carObjectToJson(newCar);
                            out.println(jsonNewCar);
//                            otherwise print message when failed to add, meaning entity already exists or was not able to insert 
                        } else {
                            System.out.println("Server message: Entity failed to add.");
                        }
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
