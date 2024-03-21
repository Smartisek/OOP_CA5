package BusinessObjects;

import DAOs.CarDaoInterface;
import DAOs.MySqlCarDao;
import DAOs.MySqlDao;
import DTOs.CarClass;
import Exception.DaoException;
import Comparators.carYearComparatorDes;

import java.sql.SQLException;
import java.util.List;
import com.google.gson.Gson;

public class App {
    public static void main(String[] args) throws DaoException, SQLException {
        CarDaoInterface IUserDao = new MySqlCarDao();
        try {
            System.out.println("\nCall: findAllCars()");
            List<CarClass> cars = IUserDao.findAllCars();

            if (cars.isEmpty()) {
                System.out.println("No cars in the system");
            } else {
                for (CarClass car : cars) {
                    System.out.println(car.toString());
                }
                String carList = IUserDao.carListToJson(cars);
                System.out.println("*** findAllCars() into Json: ***\n" + carList);
            }

            System.out.println("\n Call: findCarById()");
            CarClass car = IUserDao.findCarById(2);
            if (car != null) { //null is returned if in is not valid
                System.out.println("Car found: " + car);
            } else {
                System.out.println("Car with this id not found in database");
            }

        } catch (DaoException e) {
            e.printStackTrace();
        }



        System.out.println("Calling inserCar(): ");
//       For now no new entity will be created as it already exists because i was testing
        CarClass newCar = IUserDao.insertCar("Mustang", "Ford", "White", 2015, 65000);
        if (newCar != null) {
            System.out.println("New entity added: " + newCar);
            String jsonCar = IUserDao.carObjectToJson(newCar);
            System.out.println("Entity in Json string:\n" + jsonCar);


        } else {
            System.out.println("Entity was not added.");
        }
//      While testing, it is already deleted so need to change id otherwise will get back a message disconnected from database
        System.out.println("Deleting an entity by id ");
        IUserDao.deleteCarById(16);



        System.out.println("\nCall findCarsUsingFilter(), sorting list by production year");
//      sorting our car list with our finCarUsingFilter(comparator) function where our comparator is a lamba expression that
//      takes in two objects c1 and c2 and then compares their production year giving us list sorted in ascending order by year
        List<CarClass> sortedCars = IUserDao.findCarsUsingFilter((c1, c2) -> Integer.compare(c1.getProduction_year(), c2.getProduction_year()));


        if (sortedCars.isEmpty()) {
            System.out.println("There are no cars in the database");
        } else {
            for (CarClass sortedcar : sortedCars) {
                System.out.println(sortedcar.toString());
            }
        }

        System.out.println("\nCall findCarUsingFilter(carYearComparatorDesc");
//        using our carYearComparator for descending order
        sortedCars = IUserDao.findCarsUsingFilter(new carYearComparatorDes());

        if (sortedCars.isEmpty()) {
            System.out.println("There are no cars in the database");
        } else {
            for (CarClass sortedcar : sortedCars) {
                System.out.println(sortedcar.toString());
            }
        }
    }

}
