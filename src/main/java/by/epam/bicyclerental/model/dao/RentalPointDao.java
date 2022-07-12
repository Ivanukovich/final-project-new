package by.epam.bicyclerental.model.dao;

import by.epam.bicyclerental.exception.DaoException;
import by.epam.bicyclerental.model.entity.Bicycle;
import by.epam.bicyclerental.model.entity.RentalPoint;

import java.util.List;
import java.util.Optional;

public interface RentalPointDao {
    List<RentalPoint> findAllRentalPoints() throws DaoException;
    Optional<RentalPoint> findByRentalPointId(long rentalPointId) throws DaoException;
    Optional<RentalPoint> findByBicycleId(long bicycleId) throws DaoException;
    boolean addBicycle(long bicycleId, long rentalPointId) throws DaoException;
    boolean create(RentalPoint rentalPoint) throws DaoException;
    boolean checkIfBicycleIsPresent(long rentalPointId, long bicycleId) throws DaoException;
    boolean deleteRentalPoint(long rentalPointId) throws DaoException;
    boolean update(RentalPoint rentalPoint) throws DaoException;
}
