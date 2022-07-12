package by.epam.bicyclerental.model.dao;

import by.epam.bicyclerental.exception.DaoException;
import by.epam.bicyclerental.model.entity.Bicycle;
import by.epam.bicyclerental.model.entity.BicycleStatus;

import java.util.List;
import java.util.Optional;

public interface BicycleDao {
    List<Bicycle> findAllBicycles() throws DaoException;
    List<Bicycle> findAllFreeBicyclesAtRentalPoint(long rentalPointId)  throws DaoException;
    List<Bicycle> findAllBicyclesAtRentalPoint(long rentalPointId) throws DaoException;
    List<Bicycle> findAllInactiveBicycles() throws DaoException;
    Optional<Bicycle> findByBicycleId(long bicycleId) throws DaoException;
    Optional<Bicycle> findByUserId(long userId) throws DaoException;
    boolean updateBicycleStatus(long bicycleId, BicycleStatus status) throws DaoException;
    boolean update(Bicycle bicycle) throws DaoException ;
    boolean create(Bicycle bicycle) throws DaoException;
    boolean rentBicycle(long bicycleId, long userid, long rentalPointId) throws DaoException;
    boolean removeBicycle(long bicycleId) throws DaoException;
    boolean deleteBicycle(long bicycleId) throws DaoException;
}
