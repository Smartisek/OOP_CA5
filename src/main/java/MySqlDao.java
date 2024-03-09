//import java.sql.Connection;
//
//public class MySqlDao {
//    public Connection getConnection() throws DaoException
//    {
//        String driver = "com.mysql.cj.jdbc.Driver";
//        String url = "jdbc:mysql://localhost:3306/car_rental";
//        String username = "root";
//        String password = "";
//        Connection connection = null;
//
//        try{
//            Class.forName(driver);
//            connection = DriverManager.getConnection(url, username, password);
//        }
//        catch (ClassNotFoundException e){
//            System.out.println("Failed to find driver class " + e.getMessage());
//            System.exit(1);
//        }
//        catch (SQLException e){
//            System.out.println("Connection failed " + e.getMessage());
//            System.exit(2);
//        }
//        return connection;
//}
