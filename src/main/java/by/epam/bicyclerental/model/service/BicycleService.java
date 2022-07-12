package by.epam.bicyclerental.model.service;

import by.epam.bicyclerental.exception.ServiceException;
import by.epam.bicyclerental.model.entity.Bicycle;
import by.epam.bicyclerental.model.entity.BicycleStatus;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BicycleService {
    boolean addBicycletoDataBase(Bicycle bicycle) throws ServiceException;
    boolean returnBicycle(long userId) throws ServiceException;
    boolean removeBicycle(long bicycleId) throws ServiceException;
    boolean updateBicycle(long bicycleId, String model) throws ServiceException;
    boolean updateBicycleStatus(long bicycleId, BicycleStatus status) throws ServiceException;
    boolean deleteBicycle(long bicycleId) throws ServiceException;
    Optional<Bicycle> findBicycleById(long bicycleId)  throws ServiceException ;
    List<Bicycle> findAllFreeBicyclesAtRentalPoint(long rentalPointId) throws ServiceException;
    List<Bicycle> findAllBicyclesAtRentalPoint(long rentalPointId) throws ServiceException;
    List<Bicycle> findAllInactiveBicycles() throws ServiceException;
    List<Bicycle> findAllBicycles() throws ServiceException;
    Map<Long, String> findBicycleModels() throws ServiceException;
}
