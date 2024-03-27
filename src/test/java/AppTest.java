import DAOs.CarDaoInterface;
import DAOs.MySqlCarDao;
import DTOs.CarClass;
import org.junit.jupiter.api.Test;
import Exception.DaoException;
import BusinessObjects.App;
import static org.junit.jupiter.api.Assertions.*;
public class AppTest {

//    Dominik's code for testing functionality for looking up car by id 
    @Test
    void testFindCarByIdPass() throws DaoException {
        int id = 1;

        CarDaoInterface IUserDao = new MySqlCarDao();
        CarClass expectedCar = IUserDao.findCarById(id);


        CarClass actualCar = App.findCarById(id);
        assertEquals(expectedCar, actualCar);

        System.out.println(expectedCar);
        System.out.println("\n" + actualCar);
    }

}
