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
import by.epam.bicyclerental.model.service.RentalPointService;
import by.epam.bicyclerental.model.service.UserService;
import com.mysql.cj.conf.ConnectionUrlParser;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class RentalPointServiceImpl implements RentalPointService {
    private static RentalPointServiceImpl instance;
    private static final Logger logger = LogManager.getLogger();

    private static final RentalPointDao rentalPointDao = new RentalPointDaoImpl();
    private static final BicycleDao bicycleDao = new BicycleDaoImpl();
    private static final RentRecordDao rentRecordDao = new RentRecordDaoImpl();

    public static RentalPointService getInstance() {
        if (instance == null) {
            instance = new RentalPointServiceImpl();
        }
        return instance;
    }

    @Override
    public boolean addBicycleToRentalPoint(long bicycleId, long rentalPointId) throws ServiceException {
        try {
            Optional<Bicycle> bicycle = bicycleDao.findByBicycleId(bicycleId);
            Optional<RentalPoint> rentalPoint = rentalPointDao.findByRentalPointId(rentalPointId);
            if (rentalPoint.isPresent() || bicycle.isPresent()) {
                return rentalPointDao.addBicycle(bicycleId, rentalPointId);
            }
            return false;
        }
        catch (DaoException e){
            throw new ServiceException("Exception in rental point service method addBicycletoToRentalPoint: ", e);
        }
    }

    @Override
    public boolean createRentalPoint(RentalPoint rentalPoint) throws ServiceException {
        try {
            return rentalPointDao.create(rentalPoint);
        }
        catch (DaoException e){
            throw new ServiceException("Exception in rental point service method createRentalPoint: ", e);
        }
    }

    @Override
    public boolean deleteRentalPoint(long rentalPointId) throws ServiceException {
        try {
            rentRecordDao.deleteRentByRentalPointId(rentalPointId);
            return rentalPointDao.deleteRentalPoint(rentalPointId);
        }
        catch (DaoException e){
            throw new ServiceException("Exception in rental point service method deleteRentalPoint: ", e);
        }
    }

    @Override
    public boolean editRentalPoint(long rentalPointId, String location) throws ServiceException {
        try {
            Optional<RentalPoint> rentalPoint = rentalPointDao.findByRentalPointId(rentalPointId);
            if (rentalPoint.isPresent()){
                RentalPoint editedRentalPoint = rentalPoint.get();
                editedRentalPoint.setLocation(location);
                return rentalPointDao.update(editedRentalPoint);
            }
            else {
                return false;
            }
        }
        catch (DaoException e){
            throw new ServiceException("Exception in rental point service method editRentalPoint: ", e);
        }
    }

    @Override
    public Optional<RentalPoint> findRentalPointById(long rentalPointId) throws ServiceException {
        try {
            return rentalPointDao.findByRentalPointId(rentalPointId);
        }
        catch (DaoException e){
            throw new ServiceException("Exception in rental point service method findRentalPointById: ", e);
        }
    }

    @Override
    public List<RentalPoint> findAllRentalPoints() throws ServiceException {
        try {
            return rentalPointDao.findAllRentalPoints();
        }
        catch (DaoException e){
            throw new ServiceException("Exception in rental point service method findAllRentalPoints: ", e);
        }
    }

    @Override
    public Map<Long, String> findRentalPointLocations() throws ServiceException {
        try {
            Map<Long, String> locations = new HashMap<>();
            List<RentalPoint> rentalPointList = rentalPointDao.findAllRentalPoints();
            for (RentalPoint rentalPoint: rentalPointList) {
                locations.put(rentalPoint.getRentalPointId(), rentalPoint.getLocation());
            }
            return locations;
        } catch (DaoException e) {
            throw new ServiceException("Exception in user service method blockUser: ", e);
        }
    }
}
