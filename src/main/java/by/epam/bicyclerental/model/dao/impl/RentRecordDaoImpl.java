package by.epam.bicyclerental.model.dao.impl;

import by.epam.bicyclerental.exception.DaoException;
import by.epam.bicyclerental.model.dao.RentRecordDao;
import by.epam.bicyclerental.model.entity.Bicycle;
import by.epam.bicyclerental.model.entity.RentRecord;
import by.epam.bicyclerental.model.pool.CustomConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RentRecordDaoImpl implements RentRecordDao {
    private static final Logger logger = LogManager.getLogger();

    private static final String SELECT_ALL_RENT_RECORDS_QUERY = "SELECT rent_id, bicycle_id, rental_point_id, user_id, start, finish " +
            "FROM rent;";

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

    private static final String DELETE_RENT_QUERY = "DELETE FROM rent " +
            "WHERE rent_id = ?;";

    private static final String DELETE_RENTS_BY_USER_ID_QUERY = "DELETE FROM rent " +
            "WHERE user_id = ?;";

    private static final String DELETE_RENTS_BY_BICYCLE_ID_QUERY = "DELETE FROM rent " +
            "WHERE bicycle_id = ?;";

    private static final String DELETE_RENTS_BY_RENTAL_POINT_ID_QUERY = "DELETE FROM rent " +
            "WHERE rental_point_id = ?;";

    private static RentRecordDaoImpl instance;

    public static RentRecordDao getInstance() {
        if (instance == null) {
            instance = new RentRecordDaoImpl();
        }
        return instance;
    }

    @Override
    public List<RentRecord> findAllRentRecords() throws DaoException {
        List<RentRecord> rentRecordList = new ArrayList<>();
        try(Connection connection = CustomConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_RENT_RECORDS_QUERY)){
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                rentRecordList.add(extract(resultSet));
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception while finding all rent records");
            throw new DaoException("Exception while finding all rent records: ", e);
        }
        return rentRecordList;
    }

    @Override
    public Optional<RentRecord> findByRentId(long rentId) throws DaoException {
        Optional<RentRecord> rent = Optional.empty();
        try(Connection connection = CustomConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_RENT_BY_ID_QUERY);
            ResultSet resultSet = statement.executeQuery()){
            statement.setLong(1, rentId);
            if (resultSet.next()) {
                rent = Optional.of(extract(resultSet));
            }
            return rent;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception while finding rent");
            throw new DaoException("Exception while finding rent: ", e);
        }
    }

    @Override
    public Optional<RentRecord> findByUserId(long userId) throws DaoException {
        Optional<RentRecord> rent = Optional.empty();
        try(Connection connection = CustomConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_RENT_BY_USER_ID_QUERY)){
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                rent = Optional.of(extract(resultSet));
            }
            return rent;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception while finding rent record by user id");
            throw new DaoException("Exception while finding rent record by user id: ", e);
        }
    }

    @Override
    public boolean create(RentRecord rentRecord) throws DaoException {
        try(Connection connection = CustomConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_RENT_QUERY)){
            statement.setLong(1, rentRecord.getBicycleId());
            statement.setLong(2, rentRecord.getRentalPointId());
            statement.setLong(3, rentRecord.getUserId());
            statement.setTimestamp(4, rentRecord.getStart());
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception while creating the rent");
            throw new DaoException("Exception while creating the rent: ", e);
        }
    }

    @Override
    public boolean finishRent(long rentId, Timestamp finish) throws DaoException {
        try(Connection connection = CustomConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(FINISH_RENT_BY_ID_QUERY)){
            statement.setTimestamp(1, finish);
            statement.setLong(2, rentId);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception while finishing the rent");
            throw new DaoException("Exception while finishing the rent: ", e);
        }
    }

    @Override
    public boolean deleteRent(long rentId) throws DaoException {
        try(Connection connection = CustomConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_RENT_QUERY)){
            statement.setLong(1, rentId);
            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            logger.log(Level.ERROR,"Exception while deleting the rent");
            throw new DaoException("Exception while deleting the rent: ", e);
        }
    }

    @Override
    public boolean deleteRentByUserId(long userId) throws DaoException {
        try(Connection connection = CustomConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_RENTS_BY_USER_ID_QUERY)){
            statement.setLong(1, userId);
            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            logger.log(Level.ERROR,"Exception while deleting the rents by user id");
            throw new DaoException("Exception while deleting the rents by user id: ", e);
        }
    }

    @Override
    public boolean deleteRentByBicycleId(long bicycleId) throws DaoException {
        try(Connection connection = CustomConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_RENTS_BY_BICYCLE_ID_QUERY)){
            statement.setLong(1, bicycleId);
            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            logger.log(Level.ERROR,"Exception while deleting the rents by bicycle id");
            throw new DaoException("Exception while deleting the rents by bicycle id: ", e);
        }
    }

    @Override
    public boolean deleteRentByRentalPointId(long rentalPointId) throws DaoException {
        try(Connection connection = CustomConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_RENTS_BY_RENTAL_POINT_ID_QUERY)){
            statement.setLong(1, rentalPointId);
            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            logger.log(Level.ERROR,"Exception while deleting the rents by bicycle id");
            throw new DaoException("Exception while deleting the rents by bicycle id: ", e);
        }
    }

    private RentRecord extract(ResultSet resultSet) throws SQLException {
        return new RentRecord.Builder()
                .userId(resultSet.getLong("user_id"))
                .rentId(resultSet.getLong("rent_id"))
                .rentalPointId(resultSet.getLong("rental_point_id"))
                .bycicleId(resultSet.getLong("bicycle_id"))
                .start(resultSet.getTimestamp("start"))
                .finish(resultSet.getTimestamp("finish"))
                .build();
    }
}
