import DAOs.CarDaoInterface;
import DAOs.MySqlCarDao;
import DTOs.CarClass;
import org.junit.jupiter.api.Test;
import Exception.DaoException;
import BusinessObjects.App;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class AppTest {

//    Dominik's code for testing functionality for looking up car by id
    @Test
    void testFindCarByIdPass() throws DaoException {
        int id = 1;
// create an instance of our userinterface
        CarDaoInterface IUserDao = new MySqlCarDao();
//        create expected carClass return
        CarClass expectedCar = IUserDao.findCarById(id);
//        create actual return in our app by calling function for handling this in app
        CarClass actualCar = App.findCarById(id);
//        check if both are same, meaning test successful
        assertEquals(expectedCar, actualCar);

        System.out.println(expectedCar);
        System.out.println("\n" + actualCar);
    }
//  Dominik's test for finding all cars
    @Test
    void testFindAllCars() throws DaoException{
//        create interface
        CarDaoInterface IUserDao = new MySqlCarDao();
//  create a list for expected cars from our interface calling the function
        List<CarClass> expectedCars = IUserDao.findAllCars();
//  create actual list of cars from our App and the function inside of it
        List<CarClass> actualCars = App.fincAllCars();
//  comparing the results, needs to be the same
        assertEquals(expectedCars, actualCars);
//  double check
        System.out.println(expectedCars);
        System.out.println(actualCars);
    }

}
