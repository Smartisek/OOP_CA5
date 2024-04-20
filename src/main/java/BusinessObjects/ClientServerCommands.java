package BusinessObjects;

import DTOs.CarClass;

import java.util.ArrayList;
import java.util.List;

/**
 * Main Author: Logan Rushe
 */
public class ClientServerCommands {
    public String DisplayCarById = "display entity";
    public String DisplayAllCars = "display all cars";
    public String AddACar = "add car";
    public String DeleteCarById = "delete car";
    public String GetImagesList = "get image list";
    public List<String> imagesList = new ArrayList<>();
    public String GetImage = "get image";
    public ClientServerCommands() {
        imagesList.add("ferrari.jpg"); // resource: https://cz.pinterest.com/pin/5488830790173669/
        imagesList.add("spiderman.jpg"); //resource https://cz.pinterest.com/pin/4081455905373051/
    }
}
