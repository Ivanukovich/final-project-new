package by.epam.bicyclerental.model.service;

import by.epam.bicyclerental.exception.DaoException;
import by.epam.bicyclerental.exception.ServiceException;
import by.epam.bicyclerental.model.entity.Bicycle;
import by.epam.bicyclerental.model.entity.RentalPoint;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RentalPointService {
    boolean addBicycleToRentalPoint(long bicycleId, long rentalPointId) throws ServiceException;
    boolean createRentalPoint(RentalPoint rentalPoint) throws ServiceException;
    boolean deleteRentalPoint(long rentalPointId) throws ServiceException;
    boolean editRentalPoint(long rentalPointId, String location) throws ServiceException;
    Optional<RentalPoint> findRentalPointById(long rentalPointId) throws ServiceException;
    List<RentalPoint> findAllRentalPoints() throws ServiceException;
    Map<Long, String> findRentalPointLocations() throws ServiceException;
}
