package BusinessObjects;

import DAOs.CarDaoInterface;
import DAOs.MySqlCarDao;
import DAOs.MySqlDao;
import DTOs.CarClass;
import Exception.DaoException;

import java.util.List;

public class App {
    public static void main(String[] args){
        CarDaoInterface IUserDao = new MySqlCarDao();

        try {
            System.out.println("\nCall findAllCars()");
            List<CarClass> cars = IUserDao.findAllCars();

            if(cars.isEmpty()){
                System.out.println("No cars in the system");
            } else {
                for(CarClass car : cars){
                    System.out.println(car.toString());
                }
            }
        } catch (DaoException e){
            e.printStackTrace();
        }
    }

}
