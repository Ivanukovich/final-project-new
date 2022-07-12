package by.epam.bicyclerental.model.service.impl;

import by.epam.bicyclerental.exception.DaoException;
import by.epam.bicyclerental.exception.ServiceException;
import by.epam.bicyclerental.model.dao.BicycleDao;
import by.epam.bicyclerental.model.dao.RentRecordDao;
import by.epam.bicyclerental.model.dao.RentalPointDao;
import by.epam.bicyclerental.model.dao.UserDao;
import by.epam.bicyclerental.model.dao.impl.BicycleDaoImpl;
import by.epam.bicyclerental.model.dao.impl.RentRecordDaoImpl;
import by.epam.bicyclerental.model.dao.impl.RentalPointDaoImpl;
import by.epam.bicyclerental.model.dao.impl.UserDaoImpl;
import by.epam.bicyclerental.model.entity.*;
import by.epam.bicyclerental.model.service.RentRecordService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

public class RentRecordServiceImpl implements RentRecordService {
    private static final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy.MM.dd.hh.mm.ss");
    private static RentRecordServiceImpl instance;
    private static Timestamp timestamp;
    private static final Logger logger = LogManager.getLogger();
    private static final RentRecordDao rentRecordDao = new RentRecordDaoImpl();
    private static final RentalPointDao rentalPointDao = new RentalPointDaoImpl();
    private static final UserDao userDao = new UserDaoImpl();
    private static final BicycleDao bicycleDao = new BicycleDaoImpl();

    public static RentRecordService getInstance() {
        if (instance == null) {
            instance = new RentRecordServiceImpl();
        }
        return instance;
    }

    @Override
    public List<RentRecord> findAllRentRecords() throws ServiceException {
        try{
            return rentRecordDao.findAllRentRecords();
        } catch (DaoException e) {
            throw new ServiceException("Exception in rent record service method findAllRentRecords: ", e);
        }
    }

    @Override
    public boolean createRent(long bicycleId, long userId) throws ServiceException {
        try {
            Optional<Bicycle> bicycle = bicycleDao.findByBicycleId(bicycleId);
            Optional<User> user = userDao.findByUserId(userId);
            Optional<RentalPoint> rentalPoint = rentalPointDao.findByBicycleId(bicycleId);

            if (bicycle.isPresent() && user.isPresent() && rentalPoint.isPresent()) {

                long rentalPointId = rentalPoint.get().getRentalPointId();
                timestamp = new Timestamp(System.currentTimeMillis());
                RentRecord rentRecord = new RentRecord.Builder()
                        .bycicleId(bicycleId)
                        .rentalPointId(rentalPointId)
                        .userId(userId)
                        .start(timestamp)
                        .build();

                if (user.get().getUserStatus() == UserStatus.INACTIVE && !user.get().isBlocked() && bicycle.get().getBicycleStatus() == BicycleStatus.FREE) {
                    return userDao.updateUserStatus(userId, UserStatus.ACTIVE) &&
                            bicycleDao.updateBicycleStatus(bicycleId, BicycleStatus.RENTED) &&
                            rentRecordDao.create(rentRecord);
                } else {
                    return false;
                }
            }
            else {
                return false;
            }
        }
        catch (DaoException e){
            throw new ServiceException("Exception in rent service createRent: ", e);
        }
    }

    @Override
    public boolean finishRent(RentRecord rentRecord) throws ServiceException {
        try {
            timestamp = new Timestamp(System.currentTimeMillis());
            bicycleDao.updateBicycleStatus(rentRecord.getBicycleId(), BicycleStatus.FREE);
            userDao.updateUserStatus(rentRecord.getUserId(), UserStatus.INACTIVE);
            rentRecordDao.finishRent(rentRecord.getRentRecordId(), timestamp);
            return  true;
        }
        catch (DaoException e){
            throw new ServiceException("Exception in rent service finishRent: ", e);
        }
    }

    @Override
    public Optional<RentRecord> findRentByUserId(long userId) throws ServiceException {
        try {
            return rentRecordDao.findByUserId(userId);
        } catch (DaoException e) {
            throw new ServiceException("Exception in rent service findRentByUserId: ", e);
        }
    }

    @Override
    public boolean deleteRentRecord(long rentRecordId) throws ServiceException {
        try {
            return rentRecordDao.deleteRent(rentRecordId);
        } catch (DaoException e) {
            throw new ServiceException("Exception in rent service deleteRentRecord: ", e);
        }
    }
}
