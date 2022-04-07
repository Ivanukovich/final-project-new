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
import by.epam.bicyclerental.model.service.RentalPointService;
import by.epam.bicyclerental.model.service.UserService;
import com.mysql.cj.conf.ConnectionUrlParser;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RentalPointServiceImpl implements RentalPointService {
    private static RentalPointServiceImpl instance;
    private static final Logger logger = LogManager.getLogger();
    private static final RentalPointDao rentalPointDao = new RentalPointDaoImpl();
    private static final BicycleDao bicycleDao = new BicycleDaoImpl();
    private static final UserDao userDao = new UserDaoImpl();

    public static RentalPointService getInstance() {
        if (instance == null) {
            instance = new RentalPointServiceImpl();
        }
        return instance;
    }

    @Override
    public boolean addBicycletoToRentalPoint(long bicycle_id, long rental_point_id) throws ServiceException {
        try {
            Bicycle bicycle = bicycleDao.findByBicycleId(bicycle_id);
            RentalPoint rentalPoint = rentalPointDao.findByRentalPointId(rental_point_id);
            if (rentalPoint == null || bicycle == null) {
                return false;
            }
            return rentalPointDao.addBicycle(bicycle_id, rental_point_id);
        }
        catch (DaoException e){
            throw new ServiceException("", e);
        }
    }

    @Override
    public boolean createRentalPoint(RentalPoint rentalPoint) throws ServiceException {
        try {
            return rentalPointDao.create(rentalPoint);
        }
        catch (DaoException e){
            throw new ServiceException("", e);
        }
    }

    @Override
    public RentalPoint findRentalPointById(long rental_point_id) throws ServiceException {
        try {
            return rentalPointDao.findByRentalPointId(rental_point_id);
        }
        catch (DaoException e){
            throw new ServiceException("", e);
        }
    }

    @Override
    public List<RentalPoint> findAllRentalPoints() throws ServiceException {
        try {
            List<RentalPoint> rentalPoints = rentalPointDao.findAllRentalPoints();
            return rentalPoints;
        }
        catch (DaoException e){
            throw new ServiceException("", e);
        }
    }
}
