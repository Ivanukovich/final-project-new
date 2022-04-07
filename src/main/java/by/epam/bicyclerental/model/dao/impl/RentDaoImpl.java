package by.epam.bicyclerental.model.dao.impl;

import by.epam.bicyclerental.exception.DaoException;
import by.epam.bicyclerental.model.dao.RentDao;
import by.epam.bicyclerental.model.entity.Rent;
import by.epam.bicyclerental.model.entity.User;
import by.epam.bicyclerental.model.entity.UserRole;
import by.epam.bicyclerental.model.entity.UserStatus;
import by.epam.bicyclerental.model.pool.CustomConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class RentDaoImpl implements RentDao {
    private static final Logger logger = LogManager.getLogger();

    private static final String INSERT_RENT_QUERY = "INSERT INTO rent (bicycle_id, rental_point_id, user_id, start) " +
            "VALUES (?, ?, ?, ?);";

    private static final String SELECT_RENT_BY_ID_QUERY = "SELECT rent_id, bicycle_id, rental_point_id, user_id, start, finish " +
            "FROM rent " +
            "WHERE rent_id = ?;";

    private static final String SELECT_RENT_BY_USER_ID_QUERY = "SELECT rent_id, bicycle_id, rental_point_id, user_id, start, finish " +
            "FROM rent " +
            "WHERE user_id = ? AND finish IS NULL;";

    private static final String FINISH_RENT_BY_ID_QUERY = "UPDATE rent " +
            "SET finish = ? " +
            "WHERE rent_id = ?;";

    @Override
    public Rent findByRentId(long rent_id) throws DaoException {
        Rent foundRent = null;
        try {
            Connection connection = CustomConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_RENT_BY_ID_QUERY);
            statement.setLong(1, rent_id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                foundRent = extract(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to find rent: ", e);
        }
        logger.log(Level.DEBUG, "Found rent: " + foundRent);
        return foundRent;
    }

    @Override
    public Rent findByUserId(long user_id) throws DaoException {
        Rent foundRent = null;
        try {
            Connection connection = CustomConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_RENT_BY_USER_ID_QUERY);
            statement.setLong(1, user_id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                foundRent = extract(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to find rent: ", e);
        }
        logger.log(Level.DEBUG, "Found rent: " + foundRent);
        return foundRent;
    }

    @Override
    public boolean create(Rent rent) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = CustomConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(INSERT_RENT_QUERY);
            statement.setLong(1, rent.getBicycleId());
            statement.setLong(2, rent.getRentalPointId());
            statement.setLong(3, rent.getUserId());
            statement.setTimestamp(4, rent.getStart());
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException("Failed to find user: ", e);
        }
    }

    @Override
    public boolean finishRent(long rent_id, Timestamp finish) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = CustomConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(FINISH_RENT_BY_ID_QUERY);
            statement.setTimestamp(1, finish);
            statement.setLong(2, rent_id);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException("Failed to find user: ", e);
        }
    }

    private Rent extract(ResultSet resultSet) throws SQLException {
        return new Rent.Builder()
                .userId(resultSet.getLong("user_id"))
                .rentId(resultSet.getLong("rent_id"))
                .rentalPointId(resultSet.getLong("rental_point_id"))
                .bycicleId(resultSet.getLong("bicycle_id"))
                .start(resultSet.getTimestamp("start"))
                .finish(resultSet.getTimestamp("finish"))
                .build();
    }
}
