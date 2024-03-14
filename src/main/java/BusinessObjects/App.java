package BusinessObjects;

import DAOs.CarDaoInterface;
import DAOs.MySqlCarDao;
import DAOs.MySqlDao;
import DTOs.CarClass;
import Exception.DaoException;

import java.util.List;

public class App {
    public static void main(String[] args) throws DaoException {
        CarDaoInterface IUserDao = new MySqlCarDao();
        try {
            System.out.println("\nCall: findAllCars()");
            List<CarClass> cars = IUserDao.findAllCars();

            if(cars.isEmpty()){
                System.out.println("No cars in the system");
            } else {
                for(CarClass car : cars){
                    System.out.println(car.toString());
                }
            }

            System.out.println("\n Call: findCarById()");
            CarClass car = IUserDao.findCarById(2);
            if(car != null){ //null is returned if in is not valid
                System.out.println("Car found: " + car);
            } else {
                System.out.println("Car with this id not found in database");
            }

        } catch (DaoException e){
            e.printStackTrace();
        }

        int code = IUserDao.insertCar(new CarClass(1, "Civic", "Honda", "Silver", 2010, 25000));
        if(code == 1) {
            System.out.println("\nCar added successfully");
        } else if (code == 0) {
            System.out.println("\nCar already exists in table");
        }
    }


}
