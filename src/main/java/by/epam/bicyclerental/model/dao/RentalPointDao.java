package by.epam.bicyclerental.model.dao;

import by.epam.bicyclerental.exception.DaoException;
import by.epam.bicyclerental.model.entity.Bicycle;
import by.epam.bicyclerental.model.entity.RentalPoint;

import java.util.List;

public interface RentalPointDao {
    List<RentalPoint> findAllRentalPoints() throws DaoException;
    RentalPoint findByRentalPointId(long rental_point_id) throws DaoException;
    RentalPoint findByBicycleId(long bicycle_id) throws DaoException;
    boolean addBicycle(long bicycle_id, long rental_point_id) throws DaoException;
    boolean create(RentalPoint rentalPoint) throws DaoException;
    boolean checkIfBicycleIsPresent(long rental_point_id, long bicycle_id) throws DaoException;
}
