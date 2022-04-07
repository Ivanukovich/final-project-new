package by.epam.bicyclerental.model.dao.impl;

import by.epam.bicyclerental.exception.DaoException;
import by.epam.bicyclerental.model.dao.BicycleDao;
import by.epam.bicyclerental.model.entity.*;
import by.epam.bicyclerental.model.pool.CustomConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.management.relation.Role;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class BicycleDaoImpl implements BicycleDao {
    private static final Logger logger = LogManager.getLogger();

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
            "SET model_id = ? " +
            "status_id = (SELECT status_id FROM bicycle_status WHERE status = ?) " +
            "WHERE bicycle_id = ?;";

    private static final String INSERT_BICYCLE_QUERY = "INSERT INTO bicycle (model, status_id) " +
            "VALUES (?, (SELECT status_id FROM bicycle_status WHERE status = ?))";

    private static final String RENT_BICYCLE_QUERY = "INSERT INTO rent (bicycle_id, user_id, rental_point_id) " +
            "VALUES (?, ?, ?);";

    @Override
    public List<Bicycle> findAllBicycles() throws DaoException {
        List<Bicycle> bicycleList = new ArrayList<>();
        PreparedStatement statement = null;
        try{
            Connection connection = CustomConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SELECT_ALL_BICYCLES_QUERY);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                bicycleList.add(extract(resultSet));
            }
            logger.log(Level.INFO,"List: {}", bicycleList);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return bicycleList;
    }

    @Override
    public List<Bicycle> findAllFreeBicycles(long rental_point_id) throws DaoException {
        List<Bicycle> bicycleList = new ArrayList<>();
        PreparedStatement statement = null;
        try{
            Connection connection = CustomConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SELECT_ALL_FREE_BICYCLES_QUERY);
            statement.setLong(1, rental_point_id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                bicycleList.add(extract(resultSet));
            }
            logger.log(Level.INFO,"List: {}", bicycleList);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return bicycleList;
    }

    @Override
    public List<Bicycle> findAllBicyclesAtRentalPoint(long rental_point_id) throws DaoException {
        List<Bicycle> bicycleList = new ArrayList<>();
        PreparedStatement statement = null;
        try{
            Connection connection = CustomConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SELECT_ALL_BICYCLES_AT_RENTAL_POINT_QUERY);
            statement.setLong(1, rental_point_id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                bicycleList.add(extract(resultSet));
            }
            logger.log(Level.INFO,"List: {}", bicycleList);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return bicycleList;
    }

    @Override
    public List<Bicycle> findAllInactiveBicycles() throws DaoException {
        List<Bicycle> bicycleList = new ArrayList<>();
        PreparedStatement statement = null;
        try{
            Connection connection = CustomConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SELECT_ALL_INACTIVE_BICYCLES_QUERY);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                bicycleList.add(extract(resultSet));
            }
            logger.log(Level.INFO,"List: {}", bicycleList);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return bicycleList;
    }

    @Override
    public Bicycle findByBicycleId(long bicycle_id) throws DaoException {
        Bicycle bicycle = null;
        try {
            Connection connection = CustomConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_BICYCLE_ID_QUERY);
            statement.setLong(1, bicycle_id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                bicycle = extract(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("", e);
        }
        return bicycle;
    }

    @Override
    public Bicycle findByUserId(long user_id) throws DaoException {
        Bicycle bicycle = null;
        try {
            Connection connection = CustomConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_USER_ID_QUERY);
            statement.setLong(1, user_id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                bicycle = extract(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("", e);
        }
        return bicycle;
    }

    @Override
    public boolean updateBicycleStatus(long bicycleId, BicycleStatus status) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = CustomConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(UPDATE_BICYCLE_STATUS_QUERY);
            statement.setString(1, status.getStatusName());
            statement.setLong(2, bicycleId);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean update(Bicycle bicycle) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = CustomConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(UPDATE_QUERY);
            statement.setString(1, bicycle.getModel());
            statement.setString(2, bicycle.getStatus().getStatusName());
            statement.setLong(3, bicycle.getBicycleId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Failed to find user: ", e);
        }
        return true;
    }

    @Override
    public boolean create(Bicycle bicycle) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = CustomConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(INSERT_BICYCLE_QUERY);
            statement.setString(1, bicycle.getModel());
            statement.setString(2, bicycle.getStatus().getStatusName());
            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new DaoException("", e);
        }
    }

    @Override
    public boolean rentBicycle(long bicycle_id, long user_id, long rental_point_id) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = CustomConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(RENT_BICYCLE_QUERY);
            statement.setLong(1, bicycle_id);
            statement.setLong(2, user_id);
            statement.setLong(3, rental_point_id);
            return statement.executeUpdate() != 0;
        } catch (SQLException throwables) {
            throw new DaoException("");
        }
    }

    @Override
    public boolean removeBicycle(long bicycle_id) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = CustomConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(REMOVE_BICYCLE_QUERY);
            statement.setLong(1, bicycle_id);
            return statement.executeUpdate() != 0;
        } catch (SQLException throwables) {
            throw new DaoException("Failed to find user: ");
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
