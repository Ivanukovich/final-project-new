package by.epam.bicyclerental.model.dao;

import by.epam.bicyclerental.exception.DaoException;
import by.epam.bicyclerental.model.entity.Rent;
import by.epam.bicyclerental.model.entity.User;

import java.sql.Timestamp;

public interface RentDao {
    Rent findByRentId(long rent_id) throws DaoException;
    Rent findByUserId(long user_id) throws DaoException;
    boolean create(Rent rent) throws DaoException;
    boolean finishRent(long rent_id, Timestamp finish) throws DaoException;
}
