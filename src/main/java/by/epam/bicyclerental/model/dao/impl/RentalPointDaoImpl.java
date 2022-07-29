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
import java.util.Optional;

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

    private static final String DELETE_RENTAL_POINT_QUERY = "DELETE FROM rental_point " +
            "WHERE rental_point_id = ?;";


    private static final String UPDATE_QUERY = "UPDATE rental_point " +
            "SET location = ? " +
            "WHERE rental_point_id = ?;";

    private static RentalPointDaoImpl instance;

    public static RentalPointDao getInstance() {
        if (instance == null) {
            instance = new RentalPointDaoImpl();
        }
        return instance;
    }

    @Override
    public List<RentalPoint> findAllRentalPoints() throws DaoException {
        List<RentalPoint> rentalPoints = new ArrayList<>();
        try(Connection connection = CustomConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_RENTAL_POINTS_QUERY)){
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                rentalPoints.add(extract(resultSet));
            }
            logger.log(Level.INFO,"Rental point list: {} ", rentalPoints);
            return rentalPoints;
        } catch (SQLException e) {
            logger.log(Level.ERROR,"Exception while finding all rental points");
            throw new DaoException("Exception while finding all rental points: ", e);
        }
    }

    @Override
    public Optional<RentalPoint> findByRentalPointId(long rentalPointId) throws DaoException {
        Optional<RentalPoint> rentalPoint = Optional.empty();
        try(Connection connection = CustomConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_RENTAL_POINT_ID_QUERY)){
            statement.setLong(1, rentalPointId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                rentalPoint = Optional.of(extract(resultSet));
            }
            return rentalPoint;
        } catch (SQLException e) {
            logger.log(Level.ERROR,"Exception while finding rental point by id");
            throw new DaoException("Exception while finding rental point by id: ", e);
        }
    }

    @Override
    public Optional<RentalPoint> findByBicycleId(long bicycleId) throws DaoException {
        Optional<RentalPoint> rentalPoint = Optional.empty();
        try(Connection connection = CustomConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_RENTAL_POINT_BICYCLE_QUERY)){
            statement.setLong(1, bicycleId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                rentalPoint = Optional.of(extract(resultSet));
            }
            return rentalPoint;
        } catch (SQLException e) {
            logger.log(Level.ERROR,"Exception while finding rental point by bicycle id");
            throw new DaoException("Exception while finding rental point by bicycle id: ", e);
        }
    }


    @Override
    public boolean addBicycle(long bicycleId, long rentalPointId) throws DaoException {
        try(Connection connection = CustomConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(ADD_BICYCLE_QUERY)){
            statement.setLong(1, bicycleId);
            statement.setLong(2, rentalPointId);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.log(Level.ERROR,"Exception while adding the bicycle to rental point");
            throw new DaoException("Exception while adding the bicycle to rental point: ", e);
        }
    }

    @Override
    public boolean create(RentalPoint rentalPoint) throws DaoException {
        try(Connection connection = CustomConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_RENTAL_POINT_QUERY)){
            statement.setString(1, rentalPoint.getLocation());
            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            logger.log(Level.ERROR,"Exception while creating rental point");
            throw new DaoException("Exception while creating rental point: ", e);
        }
    }

    @Override
    public boolean checkIfBicycleIsPresent(long rentalPointId, long bicycleId) throws DaoException {
        try(Connection connection = CustomConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_BICYCLE_RENTAL_POINT_QUERY)){
            statement.setLong(1, bicycleId);
            statement.setLong(2, rentalPointId);
            return statement.executeQuery().next();
        } catch (SQLException e) {
            logger.log(Level.ERROR,"Exception while checking bicycle presence on rental point");
            throw new DaoException("Exception while checking bicycle presence on rental point: ", e);
        }
    }

    @Override
    public boolean deleteRentalPoint(long rentalPointId) throws DaoException {
        try(Connection connection = CustomConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_RENTAL_POINT_QUERY)){
            statement.setLong(1, rentalPointId);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.log(Level.ERROR,"Exception while deleting rental point");
            throw new DaoException("Exception while deleting rental point: ", e);
        }
    }

    @Override
    public boolean update(RentalPoint rentalPoint) throws DaoException {
        try(Connection connection = CustomConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)){
            statement.setString(1, rentalPoint.getLocation());
            statement.setLong(2, rentalPoint.getRentalPointId());
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.log(Level.ERROR,"Exception while updating the rental point");
            throw new DaoException("Exception while updating the rental point: ", e);
        }
    }

    private RentalPoint extract(ResultSet resultSet) throws SQLException {
        return new RentalPoint.Builder()
                .rentalPointId(resultSet.getLong("rental_point_id"))
                .location(resultSet.getString("location"))
                .build();
    }
}
