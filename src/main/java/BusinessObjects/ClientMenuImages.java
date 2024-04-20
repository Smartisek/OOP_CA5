package BusinessObjects;

import DAOs.JsonConverter;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;
import java.util.SimpleTimeZone;

public class ClientMenuImages {
    public static void main(String[] args) throws IOException {
        ClientMenuImages clientMenu = new ClientMenuImages();
        clientMenu.start();
    }

    public void start() throws IOException {
        try (Socket socket = new Socket("localhost", 7777);
             // printWritter send data to server
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             // bufferedReader to receive data from server
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ){
            System.out.println("Client Menu message: The Client Menu is running and has connected to the server.");
            out.println("imagesClient"); //for server to know which client is talking to it
            Scanner consoleInput = new Scanner(System.in);
            System.out.println("Valid commands: " + "\n" + "get image list" + "\n" + "get image");
            String userInput = "";
            ClientServerCommands commands = new ClientServerCommands();
            JsonConverter jsonConverter = new JsonConverter();
            DataInputStream dataInputStream = null;

            while (!userInput.startsWith("quit")){
                System.out.println("Enter command: ");
                userInput = consoleInput.nextLine();
                out.println(userInput);
/**
 * Main Author: Logan Rushe getting images logic, Dominik Domalip structure of clients and server
 */
                if(userInput.startsWith(commands.GetImagesList)){
                    String response = in.readLine(); //wait for response
                    System.out.println("Client: Response from server: " + response + " ");
                    List<String> imageList = jsonConverter.JsonToStringList(response); //adding response from server to a list
                    System.out.println("To select an image to download, type \"get image [imageName].jpg\"\nAvailable Images: ");
                    for(String image : imageList) {
                        System.out.println(image);
                    }
                } else if(userInput.startsWith(commands.GetImage)){
                    String response = in.readLine();

                    if(response.equals("ERROR: File not found")){
                        System.out.println("Client: Response from server: " + response);
                    } else {
                        System.out.println("Server: " + response);
                        dataInputStream = new DataInputStream(socket.getInputStream());

                        String[] userCommands = userInput.split(" ");
                        String directoryName = "Received images";
                        File directory = new File(directoryName);
                        if(!directory.exists()){
                            directory.mkdirs(); //create directories to images
                        }
                        String fileName = directoryName + userCommands[2];
                        int bytes = 0;
                        FileOutputStream fileOutputStream = new FileOutputStream(fileName);

                        //read the size of the file in bytes (file length)
                        long size = dataInputStream.readLong();
                        System.out.println("Server: file size in bytes = " + size);

                        //create a buffer to receive the incoming bytes from the socket
                        byte[] buffer = new byte[4*1024]; // 4 kilobyte buffer
                        System.out.println("Client: Bytes remaining to be read from socket: ");
                        //next, read the raw bytes in chunks (buffer size) thaat make up the image file
                        while (size > 0 && (bytes = dataInputStream.read(buffer,0, (int) Math.min(buffer.length, size))) != -1){
                            //write the buffer data into the local file
                            fileOutputStream.write(buffer, 0, bytes);
                            //reduce the 'size' by the number of bytes read in
                            size -= bytes;
                            System.out.println(size + ", ");
                        }
                        System.out.println("Image Downloaded!");
                        System.out.printf("Look in the Received images folder to see the downloaded file: %s\n", fileName);
                        fileOutputStream.close(); // close the file stream
                    }
                } else if(userInput.startsWith("quit")){
                    out.println("Client menu is being terminated.");
                    System.out.println("Client Menu message: This client is being terminated.");
//                    after break statement the while loop exits and that ends the clients session, after try statement ensures that socket, printWritter and buffer are closed and disconnects from server
//                    therefore no need to close anything as try is a feature in java that will do it for us
                    break;
                }
            }
        }
    }
}
