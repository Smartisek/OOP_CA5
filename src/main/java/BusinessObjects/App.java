package BusinessObjects;

import DAOs.CarDaoInterface;
import DAOs.MySqlCarDao;
import DAOs.MySqlDao;
import DTOs.CarClass;
import Exception.DaoException;
import Comparators.carYearComparatorDes;

import java.sql.SQLException;
import java.util.List;

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
        CarClass newCar = IUserDao.insertCar("Huracan", "Lamborghini", "Silver", 2023, 250000);
        if (newCar != null) {
            System.out.println("New entity added: " + newCar);
        } else {
            System.out.println("Entity was not added.");
        }

        System.out.println("Deleting an entity by id ");
        IUserDao.deleteCarById(16);

        System.out.println("\nCall findCarsUsingFilter(), sorting list by production year");
        List<CarClass> sortedCars = IUserDao.findCarsUsingFilter((c1, c2) -> Integer.compare(c1.getProduction_year(), c2.getProduction_year()));

        if (sortedCars.isEmpty()) {
            System.out.println("There are no cars in the database");
        } else {
            for (CarClass sortedcar : sortedCars) {
                System.out.println(sortedcar.toString());
            }
        }

        System.out.println("\nCall findCarUsingFilter(carYearComparatorDesc");
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
