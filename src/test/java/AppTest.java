import DAOs.CarDaoInterface;
import DAOs.MySqlCarDao;
import DTOs.CarClass;
import org.junit.jupiter.api.Test;
import Exception.DaoException;
import BusinessObjects.App;
import Comparators.carYearComparatorDes;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class AppTest {

    /**
     * Main Author: Dominik Domalip
     */
//    ***** Dominik's code for testing functionality for looking up car by id *****
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

    /**
     * Main Author: Dominik Domalip
     */
//  ***** Dominik's test for finding all cars *****
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

    /**
     * Main Author: Dominik Domalip
     */
// Im not sure how to do this test yet as it gives me null value after actual or expected is called
//    so i will always get fail
//    @Test
//    void testInsertCar() throws DaoException {
//        String model = "911";
//        String brand = "Porsche";
//        String colour = "Black";
//        int year = 2018;
//        int price = 157959;
//        CarDaoInterface IUserDao = new MySqlCarDao();
//        CarClass actual = App.insertCar(model, brand, colour, year, price);
//        CarClass expected = IUserDao.insertCar(model, brand, colour, year, price);
//
//        assertEquals(expected, actual);
//    }

    /**
     * Main Author: Dominik Domalip
     */
//    ***** Dominik's test for order descending *****
    @Test
    void sortAllDescendingTest() throws SQLException {
        CarDaoInterface IUserDao = new MySqlCarDao();
//  populating list with expected cars
        List<CarClass> expectedOrder = IUserDao.findCarsUsingFilter(new carYearComparatorDes());
//  populating list with actual cars
        List<CarClass> actualOrder = App.sortAllDescending();
//  comparing results
        assertEquals(expectedOrder, actualOrder);
//        double check
        System.out.println("Expected: " + expectedOrder);
        System.out.println("Actual: " + actualOrder);
    }

    /**
     * Main Author: Dominik Domalip
     */
// ***** Dominik's code for order ascending *****
    @Test
    void sortAllAscendingTest() throws SQLException{
        CarDaoInterface IUserDao = new MySqlCarDao();
//   populating lists with expected order and a actual order
        List<CarClass> expectedOrder = IUserDao.findCarsUsingFilter((c1, c2) -> Integer.compare(c1.getProduction_year(), c2.getProduction_year()));
        List<CarClass> actualOrder = App.sortAllAscending();
//  comparing results
        assertEquals(expectedOrder, actualOrder);
// double check
        System.out.println("Expected: " + expectedOrder);
        System.out.println("Actual: " + actualOrder);
    }
}
