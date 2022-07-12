package by.epam.bicyclerental.model.dao;

import by.epam.bicyclerental.exception.DaoException;
import by.epam.bicyclerental.model.entity.RentRecord;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface RentRecordDao {
    List<RentRecord> findAllRentRecords() throws DaoException;
    Optional<RentRecord> findByRentId(long rentId) throws DaoException;
    Optional<RentRecord> findByUserId(long userId) throws DaoException;
    boolean create(RentRecord rentRecord) throws DaoException;
    boolean finishRent(long rentId, Timestamp finish) throws DaoException;
    boolean deleteRent(long rentId) throws DaoException;
    boolean deleteRentByUserId(long userId) throws DaoException;
    boolean deleteRentByBicycleId(long bicycleId) throws DaoException;
    boolean deleteRentByRentalPointId(long rentalPointId) throws DaoException;
}
