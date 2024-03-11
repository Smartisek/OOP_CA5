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



//    public void start() {
//
//        System.out.println("\nSample 1 - Connecting to MySQL Database called \"test\" using MySQL JDBC Driver");
//
//        String url = "jdbc:mysql://localhost/";
//        String dbName = "car_rental";
//        String userName = "root";   // default
//        String password = "";       // default
//
//        try ( Connection conn =
//                      DriverManager.getConnection(url + dbName, userName, password) )
//        {
//            System.out.println("SUCCESS ! - Your program has successfully connected to the MySql Database Server. Well done.");
//            System.out.println("... we could query the database using the SQL commands you learned in DBMS...");
//            System.out.println("... but for now, we will simply close the connection.");
//
//            System.out.println("Your program is disconnecting from the database - goodbye.");
//        }
//        catch (SQLException ex)
//        {
//            System.out.println("Failed to connect to database - check that you have started the MySQL from XAMPP, and that your connection details are correct.");
//            ex.printStackTrace();
//        }
//    }
//}