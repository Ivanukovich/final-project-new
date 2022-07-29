package by.epam.bicyclerental.model.dao.impl;

import by.epam.bicyclerental.exception.DaoException;
import by.epam.bicyclerental.model.dao.BicycleDao;
import by.epam.bicyclerental.model.entity.*;
import by.epam.bicyclerental.model.pool.CustomConnectionPool;
import by.epam.bicyclerental.model.service.BicycleService;
import by.epam.bicyclerental.model.service.impl.BicycleServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class BicycleDaoImpl implements BicycleDao {
    private static final Logger logger = LogManager.getLogger();

    private static final String DELETE_BICYCLE_QUERY = "DELETE FROM bicycle " +
            "WHERE bicycle_id = ?;";

    private static final String REMOVE_BICYCLE_QUERY = "DELETE FROM bicycle_rental_point " +
            "WHERE bicycle_id = ?;";

    private static final String SELECT_ALL_BICYCLES_QUERY = "SELECT bicycle_id, model, status " +
            "FROM bicycle " +
            "JOIN bicycle_status ON bicycle.status_id = bicycle_status.status_id ";

    private static final String SELECT_ALL_INACTIVE_BICYCLES_QUERY = "SELECT bicycle.bicycle_id, model, status " +
            "FROM bicycle " +
            "JOIN bicycle_status ON bicycle.status_id = bicycle_status.status_id " +
            "LEFT JOIN bicycle_rental_point ON bicycle.bicycle_id = bicycle_rental_point.bicycle_id " +
            "WHERE bicycle_rental_point.bicycle_id IS NULL";

    private static final String SELECT_ALL_FREE_BICYCLES_QUERY = "SELECT bicycle.bicycle_id, model, status " +
            "FROM bicycle " +
            "JOIN bicycle_status ON bicycle.status_id = bicycle_status.status_id " +
            "JOIN bicycle_rental_point ON bicycle.bicycle_id = bicycle_rental_point.bicycle_id " +
            "WHERE bicycle_status.status = \"free\" AND rental_point_id = ?;";

    private static final String SELECT_ALL_BICYCLES_AT_RENTAL_POINT_QUERY = "SELECT bicycle.bicycle_id, model, status " +
            "FROM bicycle_rental_point " +
            "JOIN bicycle ON bicycle.bicycle_id = bicycle_rental_point.bicycle_id " +
            "JOIN bicycle_status ON bicycle.status_id = bicycle_status.status_id " +
            "WHERE rental_point_id = ?;";

    private static final String SELECT_BY_BICYCLE_ID_QUERY = "SELECT bicycle_id, model, status " +
            "FROM bicycle " +
            "JOIN bicycle_status ON bicycle.status_id = bicycle_status.status_id " +
            "WHERE bicycle_id = ?;";

    private static final String SELECT_BY_USER_ID_QUERY = "SELECT bicycle.bicycle_id, model, status " +
            "FROM bicycle " +
            "JOIN rent ON bicycle.bicycle_id = rent.bicycle_id " +
            "JOIN bicycle_status ON bicycle.status_id = bicycle_status.status_id " +
            "WHERE user_id = ?;";

    private static final String UPDATE_BICYCLE_STATUS_QUERY = "UPDATE bicycle " +
            "SET status_id = (SELECT status_id FROM bicycle_status WHERE status = ?) " +
            "WHERE bicycle_id = ?;";

    private static final String UPDATE_QUERY = "UPDATE bicycle " +
            "SET model = ?, " +
            "status_id = (SELECT status_id FROM bicycle_status WHERE status = ?) " +
            "WHERE bicycle_id = ?;";

    private static final String INSERT_BICYCLE_QUERY = "INSERT INTO bicycle (model, status_id) " +
            "VALUES (?, (SELECT status_id FROM bicycle_status WHERE status = ?))";

    private static final String RENT_BICYCLE_QUERY = "INSERT INTO rent (bicycle_id, user_id, rental_point_id) " +
            "VALUES (?, ?, ?);";

    private static BicycleDaoImpl instance;

    public static BicycleDao getInstance() {
        if (instance == null) {
            instance = new BicycleDaoImpl();
        }
        return instance;
    }

    @Override
    public List<Bicycle> findAllBicycles() throws DaoException {
        List<Bicycle> bicycleList = new ArrayList<>();
        try(Connection connection = CustomConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_BICYCLES_QUERY)){
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                bicycleList.add(extract(resultSet));
            }
            logger.log(Level.INFO,"Bicycle list: {}", bicycleList);
        } catch (SQLException e) {
            throw new DaoException("Exception while finding all bicycles: ", e);
        }
        return bicycleList;
    }

    @Override
    public List<Bicycle> findAllFreeBicyclesAtRentalPoint(long rental_point_id) throws DaoException {
        List<Bicycle> bicycleList = new ArrayList<>();
        try(Connection connection = CustomConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_FREE_BICYCLES_QUERY)){
            statement.setLong(1, rental_point_id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                bicycleList.add(extract(resultSet));
            }
            logger.log(Level.INFO,"Bicycle list: {}", bicycleList);
        } catch (SQLException e) {
            logger.log(Level.ERROR,"Exception while finding all free bicycles");
            throw new DaoException("Exception while finding all free bicycles: ", e);
        }
        return bicycleList;
    }

    @Override
    public List<Bicycle> findAllBicyclesAtRentalPoint(long rental_point_id) throws DaoException {
        List<Bicycle> bicycleList = new ArrayList<>();
        try(Connection connection = CustomConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_BICYCLES_AT_RENTAL_POINT_QUERY)){
            statement.setLong(1, rental_point_id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                bicycleList.add(extract(resultSet));
            }
            logger.log(Level.INFO,"Bicycle list: {}", bicycleList);
        } catch (SQLException e) {
            logger.log(Level.ERROR,"Exception while finding all bicycles at rental point");
            throw new DaoException("Exception while finding all bicycles at rental point: ", e);
        }
        return bicycleList;
    }

    @Override
    public List<Bicycle> findAllInactiveBicycles() throws DaoException {
        List<Bicycle> bicycleList = new ArrayList<>();
        try(Connection connection = CustomConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_INACTIVE_BICYCLES_QUERY)){
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                bicycleList.add(extract(resultSet));
            }
            logger.log(Level.INFO,"Bicycle list: {}", bicycleList);
        } catch (SQLException e) {
            logger.log(Level.ERROR,"Exception while finding all inactive bicycles");
            throw new DaoException("Exception while finding all inactive bicycles: ", e);
        }
        return bicycleList;
    }

    @Override
    public Optional<Bicycle> findByBicycleId(long bicycleId) throws DaoException {
        Optional<Bicycle> bicycle = Optional.empty();
        try(Connection connection = CustomConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_BICYCLE_ID_QUERY)){
            statement.setLong(1, bicycleId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                bicycle = Optional.of(extract(resultSet));
            }
            return bicycle;
        } catch (SQLException e) {
            logger.log(Level.ERROR,"Exception while finding the bicycle by bicycle id");
            throw new DaoException("Exception while finding the bicycle by bicycle id: ", e);
        }
    }

    @Override
    public Optional<Bicycle> findByUserId(long userId) throws DaoException {
        Optional<Bicycle> bicycle = Optional.empty();
        try(Connection connection = CustomConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_USER_ID_QUERY)){
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                bicycle = Optional.of(extract(resultSet));
            }
            return bicycle;
        } catch (SQLException e) {
            logger.log(Level.ERROR,"Exception while finding the bicycle by user id");
            throw new DaoException("Exception while finding the bicycle by user id: ", e);
        }
    }

    @Override
    public boolean updateBicycleStatus(long bicycleId, BicycleStatus status) throws DaoException {
        try(Connection connection = CustomConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_BICYCLE_STATUS_QUERY)){
            statement.setString(1, status.getStatusName());
            statement.setLong(2, bicycleId);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.log(Level.ERROR,"Exception while updating the bicycle status");
            throw new DaoException("Exception while updating the bicycle status: ", e);
        }
    }

    @Override
    public boolean update(Bicycle bicycle) throws DaoException {
        try(Connection connection = CustomConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)){
            statement.setString(1, bicycle.getModel());
            statement.setString(2, bicycle.getBicycleStatus().getStatusName());
            statement.setLong(3, bicycle.getBicycleId());
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.log(Level.ERROR,"Exception while updating the bicycle");
            throw new DaoException("Exception while updating the bicycle: ", e);
        }
    }

    @Override
    public boolean create(Bicycle bicycle) throws DaoException {
        try(Connection connection = CustomConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_BICYCLE_QUERY)){
            statement.setString(1, bicycle.getModel());
            statement.setString(2, bicycle.getBicycleStatus().getStatusName());
            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            logger.log(Level.ERROR,"Exception while creating the bicycle");
            throw new DaoException("Exception while creating the bicycle: ", e);
        }
    }

    @Override
    public boolean rentBicycle(long bicycleId, long userId, long rentalPointId) throws DaoException {
        try(Connection connection = CustomConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(RENT_BICYCLE_QUERY)){
            statement.setLong(1, bicycleId);
            statement.setLong(2, userId);
            statement.setLong(3, rentalPointId);
            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            logger.log(Level.ERROR,"Exception while renting the bicycle");
            throw new DaoException("Exception while renting the bicycle: ", e);
        }
    }

    @Override
    public boolean removeBicycle(long bicycleId) throws DaoException {
        try(Connection connection = CustomConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(REMOVE_BICYCLE_QUERY)){
            statement.setLong(1, bicycleId);
            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            logger.log(Level.ERROR,"Exception while removing the bicycle");
            throw new DaoException("Exception while removing the bicycle: ", e);
        }
    }

    @Override
    public boolean deleteBicycle(long bicycleId) throws DaoException {
        try(Connection connection = CustomConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_BICYCLE_QUERY)){
            statement.setLong(1, bicycleId);
            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            logger.log(Level.ERROR,"Exception while deleting the bicycle");
            throw new DaoException("Exception while deleting the bicycle: ", e);
        }
    }

    private Bicycle extract(ResultSet resultSet) throws SQLException {
        return new Bicycle.Builder()
                .bicycleId(resultSet.getLong("bicycle_id"))
                .model(resultSet.getString("model"))
                .status(BicycleStatus.valueOf(resultSet.getString("status").toUpperCase()))
                .build();
    }
}
