package by.epam.bicyclerental.model.service;

import by.epam.bicyclerental.exception.ServiceException;
import by.epam.bicyclerental.model.entity.Bicycle;

import java.util.List;

public interface BicycleService {
    boolean addBicycletoDataBase(Bicycle bicycle) throws ServiceException;
    boolean returnBicycle(long userId) throws ServiceException;
    boolean removeBicycle(long bicycleId) throws ServiceException;
    Bicycle findBicycleById(long bicycleId)  throws ServiceException ;
    List<Bicycle> findAllFreeBicyclesAtRentalPoint(long rentalPointId) throws ServiceException;
    List<Bicycle>  findAllInactiveBicycles() throws ServiceException;
}
