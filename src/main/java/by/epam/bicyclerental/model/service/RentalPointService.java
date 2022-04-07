package by.epam.bicyclerental.model.service;

import by.epam.bicyclerental.exception.DaoException;
import by.epam.bicyclerental.exception.ServiceException;
import by.epam.bicyclerental.model.entity.Bicycle;
import by.epam.bicyclerental.model.entity.RentalPoint;

import java.util.List;
import java.util.Map;

public interface RentalPointService {
    boolean addBicycletoToRentalPoint(long bicycle_id, long rental_point_id) throws ServiceException;
    boolean createRentalPoint(RentalPoint rentalPoint) throws ServiceException;
    RentalPoint findRentalPointById(long rental_point_id) throws ServiceException;
    List<RentalPoint> findAllRentalPoints() throws ServiceException;
}
