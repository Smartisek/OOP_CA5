package DAOs;
import DTOs.CarClass;
import Exception.DaoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlCarDao extends MySqlDao implements CarDaoInterface{
    @Override
    public List<CarClass> findAllCars() throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<CarClass> carList = new ArrayList<>();
        try {
            connection =  this.getConnection();

            String query = "SELECT * FROM car";
            preparedStatement = connection.prepareStatement(query);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt("ID");
                String model = resultSet.getString("MODEL");
                String brand = resultSet.getString("BRAND");
                String colour = resultSet.getString("COLOUR");
                int production_year = resultSet.getInt("PRODUCTION_YEAR");
                int price = resultSet.getInt("PRICE");

                CarClass u = new CarClass(id, model, brand, colour, production_year, price);
                carList.add(u);
            }
        } catch (SQLException e){
            throw new DaoException("FindAllCarResultSet()" + e.getMessage());
        } finally {
            try {
                if(resultSet != null){
                    resultSet.close();
                }
                if(preparedStatement != null){
                    preparedStatement.close();
                }
                if(connection != null){
                    freeConnection(connection);
                }
            } catch (SQLException e){
                throw new DaoException("FindAllCars()" + e.getMessage());
            }
        }
        return carList;
    }

    //    Feature 2 – Find and Display (a single) Entity by Key
//    e.g. getPlayerById(id ) – return a single entity (DTO) and display its contents.

//    **** Dominik's code ****
    public CarClass findCarById(int id) throws DaoException{
//        Declaring needed variables, setting them to null for now
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        CarClass car = null;
        //            Everything in the try block will try to execute code inside, if not
        try {
// setting connection variable to the function that connects to our database
            connection = this.getConnection();
//            Storing our sql query into a string variable, ? gets defined below
            String query = "SELECT * FROM car WHERE id = ?";
//  Prepares the statement to be sent into the database
            preparedStatement = connection.prepareStatement(query);
//            in our query setting the ? to be a int of a value of id that's passed into the function
            preparedStatement.setInt(1, id);
// finally our sql statement gets executed by calling executeQuery();
            resultSet = preparedStatement.executeQuery();
//            if there is data in the resultSet do this
            if(resultSet.next()){
//  retrieves the data from database and stores them into our variables
                int carId = resultSet.getInt("ID");
                String model = resultSet.getString("MODEL");
                String brand = resultSet.getString("BRAND");
                String colour = resultSet.getString("COLOUR");
                int production_year = resultSet.getInt("PRODUCTION_YEAR");
                int price = resultSet.getInt("PRICE");
// then create a new object of a type CarClass and put our values into it, if it is found
                car = new CarClass(carId, model, brand, colour, production_year, price);
            }
//            catch if our try block does not run
        } catch (SQLException e){
            throw new DaoException("findCarById()" + e.getMessage());
//      after all this we need to make sure to close the connection
        } finally {
            try {
                if (resultSet != null){
                    resultSet.close();
                }
                if(preparedStatement != null){
                    preparedStatement.close();
                }
//                call our function from mysqldao that will close the system
                if(connection != null){
                    freeConnection(connection);
                }
//                if anything goes wrong throw exception
            } catch (SQLException e){
                throw new DaoException("findCarById()" + e.getMessage());
            }
        }
//        at the end return our car object if it was found
        return car;
    }


}
