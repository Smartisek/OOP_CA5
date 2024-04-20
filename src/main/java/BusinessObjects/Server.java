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

/**
 * Main Author: Logan Rushe for thread logic and image logic, rest communication with clients Dominik Domalip
 */
public class Server {
    final int SERVER_PORT_NUMBER = 7777;

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

public void start(){
    ServerSocket serverSocket =null;
    Socket clientSocket =null;

    try {
        serverSocket = new ServerSocket(SERVER_PORT_NUMBER);
        System.out.println("Server has started.");
        int clientNumber = 0;  // a number sequentially allocated to each new client (for identification purposes here)

        while (true) {
            // System.out.println("Server: Listening/waiting for connections on port ..." + SERVER_PORT_NUMBER);
            System.out.println("Server: Listening for connections on port ..." + SERVER_PORT_NUMBER);
            clientSocket = serverSocket.accept();
            clientNumber++;

            System.out.println("Server: Client " + clientNumber + " has connected.");
            System.out.println("Server: Port number of remote client: " + clientSocket.getPort());
            System.out.println("Server: Port number of the socket used to talk with client " + clientSocket.getLocalPort());

            // create a new ClientHandler for the requesting client, passing in the socket and client number,
            // pass the handler into a new thread, and start the handler running in the thread.
            Thread t = new Thread(new ClientHandler(clientSocket, clientNumber));
            t.start();

            System.out.println("Server: ClientHandler started in thread " + t.getName() + " for client " + clientNumber + ". ");

        }
    } catch (IOException ex) {
        System.out.println(ex);
    }
    finally{
        try {
            if(clientSocket!=null)
                clientSocket.close();
        } catch (IOException e) {
            System.out.println(e);
        }
        try {
            if(serverSocket!=null)
                serverSocket.close();
        } catch (IOException e) {
            System.out.println(e);
        }

    }
    System.out.println("Server: Server exiting, Goodbye!");
}


class ClientHandler implements Runnable   // each ClientHandler communicates with one Client
{
    BufferedReader socketReader;
    PrintWriter socketWriter;
    Socket clientSocket;
    final int clientNumber;

    // Constructor
    public ClientHandler(Socket clientSocket, int clientNumber) {
        this.clientSocket = clientSocket;  // store socket for closing later
        this.clientNumber = clientNumber;  // ID number that we are assigning to this client
        try {
            // assign to fields
            this.socketWriter = new PrintWriter(clientSocket.getOutputStream(), true);
            this.socketReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        String request;
        ClientServerCommands commands = new ClientServerCommands();
        DataOutputStream dataOutputStream = null;
        try {
            while ((request = socketReader.readLine()) != null) {
                System.out.println("Server: (ClientHandler): Read command from client " + clientNumber + ": " + request);
                // Implement PROTOCOL
                if (request.equals("databaseClient")) {
                    handleDatabaseClient(request, commands, socketWriter, socketReader);
                } else if(request.equals("imagesClient")){
                    handleImagesClient(request, commands, socketWriter, socketReader, dataOutputStream);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleImagesClient(String request, ClientServerCommands commands, PrintWriter socketWriter, BufferedReader socketReader, DataOutputStream dataOutputStream) throws DaoException, IOException {
        JsonConverter JsonConverter = new JsonConverter();

        while ((request = socketReader.readLine()) != null) {
            if (request.startsWith(commands.GetImagesList)) {
                String imagesListJson = JsonConverter.stringListToJson(commands.imagesList);
                socketWriter.println(imagesListJson);
                System.out.printf("Server: JSON of images List sent to client %d\n", clientNumber);
            } else if(request.startsWith(commands.GetImage)){
                try {
                    dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
                    System.out.printf("Preparing to send file to Client %d...\n", clientNumber);
                    String[] requestCommands = request.split(" "); // Split request into parts
                    String imageName = "Images/" + requestCommands[2];  // Define image path

                    int bytes = 0;
                    // Open the File at the specified location (path)
                    File file = new File(imageName);
                    FileInputStream fileInputStream = new FileInputStream(file);

                    System.out.printf("Sending the file to Client %d...\n", clientNumber);
                    socketWriter.println("Sending...");

                    // send the length (in bytes) of the file to the server
                    dataOutputStream.writeLong(file.length());

                    // Break file into chunks
                    byte[] buffer = new byte[4 * 1024]; // 4 kilobyte buffer
                    // read bytes from file into the buffer until buffer is full, or we reached end of file
                    while ((bytes = fileInputStream.read(buffer)) != -1){
                        dataOutputStream.write(buffer, 0, bytes);
                        dataOutputStream.flush(); // force the data into the stream
                    }
                    System.out.printf("File %s sent to Client %d\n", requestCommands[2], clientNumber);
                    fileInputStream.close(); // close the file
                } catch (IOException e){
                    e.printStackTrace();
                    socketWriter.println("ERROR: File not found");
                }
            } else if (request.startsWith("quit")) {
                System.out.println("The request from client: " + request + ". Client has been terminated.");
                break;
            }
        }

    }

    private void handleDatabaseClient(String request, ClientServerCommands commands, PrintWriter socketWriter, BufferedReader socketReader) throws DaoException, IOException {
        CarDaoInterface IUserDao = new MySqlCarDao();
        JsonConverter JsonConverter = new JsonConverter();
        while ((request = socketReader.readLine()) != null) {
            if (request.startsWith(commands.DisplayCarById)) {
//                        put request into an array and split it by space
                String[] parts = request.split(" ");
//                        second part of request will be id that gets passed into IUserDao function
                int id = Integer.parseInt(parts[2]);
                CarClass car = IUserDao.findCarById(id);
//                        convert output class into JSon
                String carJson = JsonConverter.carObjectToJson(car);
                socketWriter.println(carJson);
            } else if (request.startsWith(commands.DisplayAllCars)) {
                List<CarClass> allCars = IUserDao.findAllCars();
                String carsJson = JsonConverter.carListToJson(allCars); //*********
                System.out.println("Server Message: Sending to Client Menu: " + "\n" + carsJson + "\n");
                socketWriter.println(carsJson);
            } else if (request.startsWith(commands.AddACar)) {
//                        get the request from client in json format
                String jsonRequest = socketReader.readLine();
                System.out.println("Server message: Server receives " + jsonRequest);
//                        convert json request into car class object
                CarClass carRequest = JsonConverter.fromJson(jsonRequest);
//                        calling onto insertCar function in DAO with our interface and passing in requested data from carRequest instance
                CarClass newCar = IUserDao.insertCar(carRequest.getModel(), carRequest.getBrand(), carRequest.getColour(), carRequest.getProduction_year(), carRequest.getPrice());
//                        if newCar is not null meaning it was added so print message and send result to client
                if (newCar != null) {
                    System.out.println("Server message: Entity successfully added. Entity: " + "\n" + newCar);
                    String jsonNewCar = JsonConverter.carObjectToJson(newCar);
                    socketWriter.println(jsonNewCar);
//                            otherwise print message when failed to add, meaning entity already exists or was not able to insert
                } else {
                    System.out.println("Server message: Entity failed to add.");
                }
            } else if (request.startsWith(commands.DeleteCarById)) {
//                        read request from client
                String requestDelete = socketReader.readLine();
                System.out.println("Server message: Server receives request to delete: " + requestDelete);
//                        put request into car class from json
                CarClass requestCar = JsonConverter.fromJson(requestDelete);
//                        call on delele requested car by id passing id from requestCar
                IUserDao.deleteCarById(requestCar.getId());
//                        delete check, after car was deleted, try look for it again, if not found that means delete was successful
                CarClass deleteCheck = IUserDao.findCarById(requestCar.getId());
//                        if after delete we can not find the entity so it is null, means successful delete and send message to client
                if (deleteCheck == null) {
                    System.out.println("Server message: The entity with id: " + requestCar.getId() + " was successfully deleted");
                    socketWriter.println("Delete successful for: ");
//                        if we are able to find the entity after delete than delete failed and send the message to client
                } else {
                    System.out.println("Server message: Error entity not deleted");
                    socketWriter.println("Delete failed for: ");
                }
            } else if (request.startsWith("quit")) {
                System.out.println("The request from client: " + request + ". Client has been terminated.");
                break;
            }
        }
    }
  }
}

/**
 * run() method is called by the Thread it is assigned to.
 * This code runs independently of all other threads.
 */