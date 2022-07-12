package by.epam.bicyclerental.model.service.impl;

import by.epam.bicyclerental.exception.DaoException;
import by.epam.bicyclerental.exception.ServiceException;
import by.epam.bicyclerental.model.dao.BicycleDao;
import by.epam.bicyclerental.model.dao.RentRecordDao;
import by.epam.bicyclerental.model.dao.UserDao;
import by.epam.bicyclerental.model.dao.impl.BicycleDaoImpl;
import by.epam.bicyclerental.model.dao.impl.RentRecordDaoImpl;
import by.epam.bicyclerental.model.dao.impl.UserDaoImpl;
import by.epam.bicyclerental.model.entity.*;
import by.epam.bicyclerental.model.service.BicycleService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BicycleServiceImpl implements BicycleService {
    private static final Logger logger = LogManager.getLogger();

    private static final BicycleDao bicycleDao = new BicycleDaoImpl();
    private static final UserDao userDao = new UserDaoImpl();
    private static final RentRecordDao rentDao = new RentRecordDaoImpl();
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
            throw new ServiceException("Exception in bicycle service method addBicycletoDataBase: ", e);
        }
    }

    @Override
    public boolean returnBicycle(long userId) throws ServiceException {
        try{
            Optional<Bicycle> bicycle = bicycleDao.findByUserId(userId);
            if (bicycle.isPresent()) {
                if (!bicycleDao.updateBicycleStatus(bicycle.get().getBicycleId(), BicycleStatus.FREE)) {
                    return false;
                }
                return userDao.updateUserStatus(userId, UserStatus.INACTIVE);
            }
            return false;
        }
        catch (DaoException e){
            throw new ServiceException("Exception in bicycle service method returnBicycle: ", e);
        }
    }

    @Override
    public boolean removeBicycle(long bicycleId) throws ServiceException {
        try{
            return bicycleDao.removeBicycle(bicycleId);
        } catch (DaoException e) {
            throw new ServiceException("Exception in bicycle service method removeBicycle: ", e);
        }
    }

    @Override
    public boolean updateBicycle(long bicycleId, String model) throws ServiceException {
        try {
            Optional<Bicycle> bicycle = bicycleDao.findByBicycleId(bicycleId);
            if (bicycle.isPresent()){
                Bicycle editedBicycle = bicycle.get();
                editedBicycle.setModel(model);
                return bicycleDao.update(editedBicycle);
            }
            else {
                return false;
            }
        }
        catch (DaoException e){
            throw new ServiceException("Exception in bicycle service method updateBicycle: ", e);
        }
    }

    @Override
    public boolean updateBicycleStatus(long bicycleId, BicycleStatus status) throws ServiceException {
        try {
            return bicycleDao.updateBicycleStatus(bicycleId, status);
        }
        catch (DaoException e){
            throw new ServiceException("Exception in bicycle service method updateBicycleStatus: ", e);
        }
    }

    @Override
    public boolean deleteBicycle(long bicycleId) throws ServiceException {
        try{
            bicycleDao.removeBicycle(bicycleId);
            logger.log(Level.INFO, "here1");
            rentDao.deleteRentByBicycleId(bicycleId);
            logger.log(Level.INFO, "here2");
            return bicycleDao.deleteBicycle(bicycleId);
        } catch (DaoException e) {
            throw new ServiceException("Exception in bicycle service method deleteBicycle: ", e);
        }
    }

    @Override
    public Optional<Bicycle> findBicycleById(long bicycleId) throws ServiceException {
        try{
            return bicycleDao.findByBicycleId(bicycleId);
        } catch (DaoException e) {
            throw new ServiceException("Exception in bicycle service method findBicycleById: ", e);
        }
    }

    @Override
    public List<Bicycle> findAllFreeBicyclesAtRentalPoint(long rentalPointId) throws ServiceException {
        try{
            return bicycleDao.findAllFreeBicyclesAtRentalPoint(rentalPointId);
        } catch (DaoException e) {
            throw new ServiceException("Exception in bicycle service method findAllFreeBicyclesAtRentalPoint: ", e);
        }
    }

    @Override
    public List<Bicycle> findAllBicyclesAtRentalPoint(long rentalPointId) throws ServiceException {
        try{
            return bicycleDao.findAllBicyclesAtRentalPoint(rentalPointId);
        } catch (DaoException e) {
            throw new ServiceException("Exception in bicycle service method findAllFreeBicyclesAtRentalPoint: ", e);
        }
    }



    @Override
    public List<Bicycle> findAllInactiveBicycles() throws ServiceException {
        try{
            return bicycleDao.findAllInactiveBicycles();
        } catch (DaoException e) {
            throw new ServiceException("Exception in bicycle service method findAllInactiveBicycles: ", e);
        }
    }

    @Override
    public List<Bicycle> findAllBicycles() throws ServiceException {
        try{
            return bicycleDao.findAllBicycles();
        } catch (DaoException e) {
            throw new ServiceException("Exception in bicycle service method findAllBicycles: ", e);
        }
    }

    @Override
    public Map<Long, String> findBicycleModels() throws ServiceException {
        try {
            Map<Long, String> models = new HashMap<>();
            List<Bicycle> bicycles = bicycleDao.findAllBicycles();
            for (Bicycle bicycle: bicycles) {
                models.put(bicycle.getBicycleId(), bicycle.getModel());
            }
            return models;
        } catch (DaoException e) {
            throw new ServiceException("Exception in user service method blockUser: ", e);
        }
    }
}
