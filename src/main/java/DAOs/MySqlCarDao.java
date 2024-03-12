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
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        CarClass car = null;
        try {
//            Everything in the try block will try to execute code inside, if not
            connection = this.getConnection();
            String query = "SELECT * FROM car WHERE id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                int carId = resultSet.getInt("ID");
                String model = resultSet.getString("MODEL");
                String brand = resultSet.getString("BRAND");
                String colour = resultSet.getString("COLOUR");
                int production_year = resultSet.getInt("PRODUCTION_YEAR");
                int price = resultSet.getInt("PRICE");

                car = new CarClass(carId, model, brand, colour, production_year, price);
            }
        } catch (SQLException e){
            throw new DaoException("findCarById()" + e.getMessage());
        } finally {
            try {
                if (resultSet != null){
                    resultSet.close();
                }
                if(preparedStatement != null){
                    preparedStatement.close();
                }
                if(connection != null){
                    freeConnection(connection);
                }
            } catch (SQLException e){
                throw new DaoException("findCarById()" + e.getMessage());
            }
        }
        return car;
    }


}
