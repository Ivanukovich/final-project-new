package by.epam.bicyclerental.model.service.impl;

import by.epam.bicyclerental.exception.DaoException;
import by.epam.bicyclerental.exception.ServiceException;
import by.epam.bicyclerental.model.dao.BicycleDao;
import by.epam.bicyclerental.model.dao.RentalPointDao;
import by.epam.bicyclerental.model.dao.UserDao;
import by.epam.bicyclerental.model.dao.impl.BicycleDaoImpl;
import by.epam.bicyclerental.model.dao.impl.RentalPointDaoImpl;
import by.epam.bicyclerental.model.dao.impl.UserDaoImpl;
import by.epam.bicyclerental.model.entity.*;
import by.epam.bicyclerental.model.service.BicycleService;
import by.epam.bicyclerental.model.service.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class BicycleServiceImpl implements BicycleService {
    BicycleDao bicycleDao = new BicycleDaoImpl();
    UserDao userDao = new UserDaoImpl();
    RentalPointDao rentalPointDao = new RentalPointDaoImpl();
    private static final Logger logger = LogManager.getLogger();
    private static BicycleServiceImpl instance;

    public static BicycleService getInstance() {
        if (instance == null) {
            instance = new BicycleServiceImpl();
        }
        return instance;
    }

    @Override
    public boolean addBicycletoDataBase(Bicycle bicycle) throws ServiceException {
        try {
            return bicycleDao.create(bicycle);
        }
        catch (DaoException e){
            throw new ServiceException("", e);
        }
    }

    @Override
    public boolean returnBicycle(long user_id) throws ServiceException {
        try{
            Bicycle bicycle = bicycleDao.findByUserId(user_id);
            if(!bicycleDao.updateBicycleStatus(bicycle.getBicycleId(), BicycleStatus.FREE))
            {
                return false;
            };
            logger.log(Level.INFO,"3");
            if(!userDao.updateUserStatus(user_id, UserStatus.INACTIVE))
            {
                return false;
            };
            return true;
        }
        catch (DaoException e){
            throw new ServiceException("", e);
        }
    }

    @Override
    public boolean removeBicycle(long bicycleId) throws ServiceException {
        try{
            return bicycleDao.removeBicycle(bicycleId);
        } catch (DaoException e) {
            throw new ServiceException("", e);
        }
    }

    @Override
    public Bicycle findBicycleById(long bicycleId) throws ServiceException {
        try{
            return bicycleDao.findByBicycleId(bicycleId);
        } catch (DaoException e) {
            throw new ServiceException("", e);
        }
    }

    @Override
    public List<Bicycle> findAllFreeBicyclesAtRentalPoint(long rentalPointId) throws ServiceException {
        try{
            List<Bicycle> bicycleList = bicycleDao.findAllFreeBicycles(rentalPointId);
            return bicycleList;
        } catch (DaoException e) {
            throw new ServiceException("", e);
        }
    }

    @Override
    public List<Bicycle> findAllInactiveBicycles() throws ServiceException {
        try{
            List<Bicycle> bicycleList = bicycleDao.findAllInactiveBicycles();
            return bicycleList;
        } catch (DaoException e) {
            throw new ServiceException("", e);
        }
    }
}
