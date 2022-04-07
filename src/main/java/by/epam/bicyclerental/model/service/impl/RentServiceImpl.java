package by.epam.bicyclerental.model.service.impl;

import by.epam.bicyclerental.exception.DaoException;
import by.epam.bicyclerental.exception.ServiceException;
import by.epam.bicyclerental.model.dao.BicycleDao;
import by.epam.bicyclerental.model.dao.RentDao;
import by.epam.bicyclerental.model.dao.RentalPointDao;
import by.epam.bicyclerental.model.dao.UserDao;
import by.epam.bicyclerental.model.dao.impl.BicycleDaoImpl;
import by.epam.bicyclerental.model.dao.impl.RentDaoImpl;
import by.epam.bicyclerental.model.dao.impl.RentalPointDaoImpl;
import by.epam.bicyclerental.model.dao.impl.UserDaoImpl;
import by.epam.bicyclerental.model.entity.*;
import by.epam.bicyclerental.model.service.BicycleService;
import by.epam.bicyclerental.model.service.RentService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class RentServiceImpl implements RentService {
    private static final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy.MM.dd.hh.mm.ss");
    private static RentServiceImpl instance;
    private static Timestamp timestamp;
    private static final Logger logger = LogManager.getLogger();
    private static final RentDao rentDao = new RentDaoImpl();
    private static final RentalPointDao rentalPointDao = new RentalPointDaoImpl();
    private static final UserDao userDao = new UserDaoImpl();
    private static final BicycleDao bicycleDao = new BicycleDaoImpl();

    public static RentService getInstance() {
        if (instance == null) {
            instance = new RentServiceImpl();
        }
        return instance;
    }

    @Override
    public boolean createRent(long bicycle_id, long user_id) throws ServiceException {
        try {
            Bicycle bicycle = bicycleDao.findByBicycleId(bicycle_id);
            User user = userDao.findByUserId(user_id);
            RentalPoint rentalPoint = rentalPointDao.findByBicycleId(bicycle_id);
            long rental_point_id = rentalPoint.getRentalPointId();
            timestamp = new Timestamp(System.currentTimeMillis());
            Rent rent = new Rent.Builder()
                    .bycicleId(bicycle_id)
                    .rentalPointId(rental_point_id)
                    .userId(user_id)
                    .start(timestamp)
                    .build();
            if (user.getUserStatus() == UserStatus.INACTIVE && !user.isBlocked() && bicycle.getStatus() == BicycleStatus.FREE) {
                return userDao.updateUserStatus(user_id, UserStatus.ACTIVE) &&
                        bicycleDao.updateBicycleStatus(bicycle_id, BicycleStatus.ACTIVE) &&
                        rentDao.create(rent);
            }
            else {
                return false;
            }
        }
        catch (DaoException e){
            throw new ServiceException("", e);
        }
    }

    @Override
    public boolean finishRent(Rent rent) throws ServiceException {
        try {
            timestamp = new Timestamp(System.currentTimeMillis());
            bicycleDao.updateBicycleStatus(rent.getBicycleId(), BicycleStatus.FREE);
            userDao.updateUserStatus(rent.getUserId(), UserStatus.INACTIVE);
            rentDao.finishRent(rent.getRentId(), timestamp);
            return  true;
        }
        catch (DaoException e){
            throw new ServiceException("", e);
        }
    }

    @Override
    public Rent findRentByUserId(long user_id) throws ServiceException {
        Rent rent;
        try {
            rent = rentDao.findByUserId(user_id);
        } catch (DaoException e) {
            throw new ServiceException("findById() - Failed to find opened rent of user " + user_id, e);
        }
        logger.log(Level.DEBUG, "Found opened rent by user_id {}: {}", user_id, rent);
        return rent;
    }
}
