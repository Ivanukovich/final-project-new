package by.epam.bicyclerental.model.service;

import by.epam.bicyclerental.exception.ServiceException;
import by.epam.bicyclerental.model.entity.Rent;

public interface RentService {
    boolean createRent(long bicycle_id, long user_id) throws ServiceException;
    boolean finishRent(Rent rent) throws ServiceException;
    Rent findRentByUserId(long user_id) throws ServiceException;
}
