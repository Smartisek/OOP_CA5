import java.sql.Connection;

public class MySqlDao {
    public Connection getConnection() throws DaoException
    {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/user_database";
        String username = "
}
