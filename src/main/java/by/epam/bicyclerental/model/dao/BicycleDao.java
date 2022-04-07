package by.epam.bicyclerental.model.dao;

import by.epam.bicyclerental.exception.DaoException;
import by.epam.bicyclerental.model.entity.Bicycle;
import by.epam.bicyclerental.model.entity.BicycleStatus;

import java.util.List;

public interface BicycleDao {
    List<Bicycle> findAllBicycles() throws DaoException;
    List<Bicycle> findAllFreeBicycles(long rental_point_id)  throws DaoException;
    List<Bicycle> findAllBicyclesAtRentalPoint(long rental_point_id) throws DaoException;
    List<Bicycle> findAllInactiveBicycles() throws DaoException;
    Bicycle findByBicycleId(long bicycle_id) throws DaoException;
    Bicycle findByUserId(long user_id) throws DaoException;
    boolean updateBicycleStatus(long id, BicycleStatus status) throws DaoException;
    boolean update(Bicycle bicycle) throws DaoException ;
    boolean create(Bicycle bicycle) throws DaoException;
    boolean rentBicycle(long bicycle_id, long user_id, long rental_point_id) throws DaoException;
    boolean removeBicycle(long bicycle_id) throws DaoException;
}
