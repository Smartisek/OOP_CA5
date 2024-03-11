package Exception;

import java.sql.SQLException;
public class DaoException extends Exception {
    public DaoException() {
        // not used
    }

    public DaoException(String message) {super(message);
    }
}
