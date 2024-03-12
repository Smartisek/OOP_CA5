package DAOs;
import java.util.List;
import Exception.DaoException;
import DTOs.CarClass;
public interface CarDaoInterface {
    public List<CarClass> findAllCars() throws DaoException;
    public CarClass findCarById(int id) throws DaoException;
}
