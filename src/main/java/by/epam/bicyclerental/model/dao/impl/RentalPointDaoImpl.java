package by.epam.bicyclerental.model.dao.impl;

import by.epam.bicyclerental.exception.DaoException;
import by.epam.bicyclerental.model.dao.RentalPointDao;
import by.epam.bicyclerental.model.entity.RentalPoint;
import by.epam.bicyclerental.model.pool.CustomConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RentalPointDaoImpl implements RentalPointDao {
    private static final Logger logger = LogManager.getLogger();

    private static final String SELECT_BY_RENTAL_POINT_ID_QUERY = "SELECT rental_point_id, location " +
            "FROM rental_point " +
            "WHERE rental_point_id = ?;";

    private static final String SELECT_RENTAL_POINT_BICYCLE_QUERY = "SELECT rental_point.rental_point_id, location " +
            "FROM rental_point " +
            "JOIN bicycle_rental_point " +
            "ON rental_point.rental_point_id = bicycle_rental_point.rental_point_id " +
            "WHERE bicycle_id = ?;";

    private static final String SELECT_BICYCLE_RENTAL_POINT_QUERY = "SELECT bicycle_rental_point_id " +
            "FROM bicycle_rental_point " +
            "WHERE bicycle_id = ? AND rental_point_id = ?;";


    private static final String INSERT_RENTAL_POINT_QUERY = "INSERT INTO rental_point(location) " +
            "VALUES (?);";

    private static final String SELECT_ALL_RENTAL_POINTS_QUERY = "SELECT rental_point_id, location " +
            "FROM rental_point;";

    private static final String ADD_BICYCLE_QUERY = "INSERT INTO bicycle_rental_point (bicycle_id, rental_point_id) " +
            "VALUES (?, ?);";


    @Override
    public List<RentalPoint> findAllRentalPoints() throws DaoException {
        List<RentalPoint> rentalPoints = new ArrayList<>();
        PreparedStatement statement = null;
        try{
            Connection connection = CustomConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SELECT_ALL_RENTAL_POINTS_QUERY);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                rentalPoints.add(extract(resultSet));

            }
            logger.log(Level.INFO,"List: " + rentalPoints);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return rentalPoints;
    }

    @Override
    public RentalPoint findByRentalPointId(long rental_point_id) throws DaoException {
        RentalPoint rentalPoint = null;
        try {
            Connection connection = CustomConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_RENTAL_POINT_ID_QUERY);
            statement.setLong(1, rental_point_id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                rentalPoint = extract(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("", e);
        }
        logger.log(Level.DEBUG, "");
        return rentalPoint;
    }

    @Override
    public RentalPoint findByBicycleId(long bicycle_id) throws DaoException {
        RentalPoint rentalPoint = null;
        try {
            Connection connection = CustomConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_RENTAL_POINT_BICYCLE_QUERY);
            statement.setLong(1, bicycle_id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                rentalPoint = extract(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("", e);
        }
        logger.log(Level.DEBUG, "");
        return rentalPoint;
    }


    @Override
    public boolean addBicycle(long bicycle_id, long rental_point_id) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = CustomConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(ADD_BICYCLE_QUERY);
            statement.setLong(1, bicycle_id);
            statement.setLong(2, rental_point_id);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException("", e);
        }
    }

    @Override
    public boolean create(RentalPoint rentalPoint) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = CustomConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(INSERT_RENTAL_POINT_QUERY);
            statement.setString(1, rentalPoint.getLocation());
            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new DaoException("Failed to find user: ", e);
        }
    }

    @Override
    public boolean checkIfBicycleIsPresent(long rental_point_id, long bicycle_id) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = CustomConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SELECT_BICYCLE_RENTAL_POINT_QUERY);
            statement.setLong(1, bicycle_id);
            statement.setLong(2, rental_point_id);
            return statement.executeQuery().next();
        } catch (SQLException e) {
            throw new DaoException("Failed to find user: ", e);
        }
    }

    private RentalPoint extract(ResultSet resultSet) throws SQLException {
        return new RentalPoint.Builder()
                .rentalPointId(resultSet.getLong("rental_point_id"))
                .location(resultSet.getString("location"))
                .build();
    }
}
