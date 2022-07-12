package by.epam.bicyclerental.model.service;

import by.epam.bicyclerental.exception.ServiceException;
import by.epam.bicyclerental.model.entity.RentRecord;

import java.util.List;
import java.util.Optional;

public interface RentRecordService {
    List<RentRecord> findAllRentRecords() throws ServiceException;
    boolean createRent(long bicycleId, long userId) throws ServiceException;
    boolean finishRent(RentRecord rentRecord) throws ServiceException;
    Optional<RentRecord> findRentByUserId(long userId) throws ServiceException;
    boolean deleteRentRecord(long rentRecordId) throws ServiceException;
}
