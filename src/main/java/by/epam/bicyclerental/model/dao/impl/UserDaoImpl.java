package by.epam.bicyclerental.model.dao.impl;

import by.epam.bicyclerental.exception.DaoException;
import by.epam.bicyclerental.model.dao.UserDao;
import by.epam.bicyclerental.model.entity.User;
import by.epam.bicyclerental.model.entity.UserRole;
import by.epam.bicyclerental.model.entity.UserStatus;
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

public class UserDaoImpl implements UserDao {
    private static final Logger logger = LogManager.getLogger();

    private static final String SELECT_ALL_USERS_QUERY = "SELECT user_id, first_name, last_name, login, password, email, role, status, is_blocked " +
            "FROM user " +
            "JOIN user_role ON user.role_id = user_role.role_id " +
            "JOIN user_status ON user.status_id = user_status.status_id;";

    private static final String SELECT_ALL_ACTIVE_USERS_QUERY = "SELECT user_id, first_name, last_name, login, password, email, role, status, is_blocked " +
            "FROM user " +
            "JOIN user_role ON user.role_id = user_role.role_id " +
            "JOIN user_status ON user.status_id = user_status.status_id " +
            "WHERE user_status.status = \"active\";";

    private static final String SELECT_BY_USER_ID_QUERY = "SELECT user_id, first_name, last_name, login, password, email, role, status, is_blocked " +
            "FROM user " +
            "JOIN user_role ON user.role_id = user_role.role_id " +
            "JOIN user_status ON user.status_id = user_status.status_id " +
            "WHERE user_id = ?;";

    private static final String SELECT_BY_LOGIN_QUERY = "SELECT user_id, first_name, last_name, login, password, email, role, status, is_blocked " +
            "FROM user " +
            "JOIN user_role ON user.role_id = user_role.role_id " +
            "JOIN user_status ON user.status_id = user_status.status_id " +
            "WHERE login = ?;";

    private static final String SELECT_BY_EMAIL_QUERY = "SELECT user_id, first_name, last_name, login, password, email, role, status, is_blocked " +
            "FROM user " +
            "JOIN user_role ON user.role_id = user_role.role_id " +
            "JOIN user_status ON user.status_id = user_status.status_id " +
            "WHERE email = ?;";

    private static final String UPDATE_PASSWORD_QUERY = "UPDATE user " +
            "SET password = ? " +
            "WHERE user_id = ?;";

    private static final String UPDATE_USER_STATUS_QUERY = "UPDATE user " +
            "SET status_id = (SELECT status_id FROM user_status WHERE status = ?) " +
            "WHERE user_id = ?;";

    private static final String UPDATE_USER_ROLE_QUERY = "UPDATE user " +
            "SET role_id = (SELECT role_id FROM user_role WHERE role = ?) " +
            "WHERE user_id = ?;";

    private static final String UPDATE_QUERY =
            "UPDATE user " +
            "SET first_name = ?, last_name = ?, login = ?, password = ?, email = ?, is_blocked = ?, " +
            "status_id = (SELECT status_id FROM user_status WHERE status = ?) " +
            "role_id = (SELECT role_id FROM user_role WHERE role = ?) " +
            "WHERE user_id = ?;";

    private static final String BLOCK_USER_QUERY =
            "UPDATE user " +
            "SET is_blocked = 1 " +
            "WHERE user_id = ?;";

    private static final String UNBLOCK_USER_QUERY = "UPDATE user " +
            "SET is_blocked = 0 " +
            "WHERE user_id = ?;";

    private static final String INSERT_USER_QUERY =
            "INSERT INTO user (first_name, last_name, login, password, email, status_id, role_id, is_blocked) " +
            "VALUES (?, ?, ?, ?, ?, (SELECT status_id FROM user_status WHERE status = ?), (SELECT role_id FROM user_role WHERE role = ?), ?);";

    private static final String DELETE_USER_QUERY = "DELETE FROM user " +
            "WHERE user_id = ?;";

    private static UserDaoImpl instance;

    public UserDaoImpl() {
    }

    public static UserDaoImpl getInstance() {
        if (instance == null) {
            instance = new UserDaoImpl();
        }
        return instance;
    }

    @Override
    public List<User> findAllUsers() throws DaoException {
        List<User> userList = new ArrayList<>();
        PreparedStatement statement = null;
        try{
            Connection connection = CustomConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SELECT_ALL_USERS_QUERY);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                userList.add(extract(resultSet));
            }
            logger.log(Level.INFO,"List: " + userList);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return userList;
    }

    @Override
    public List<User> findAllActiveUsers() throws DaoException {
        List<User> userList = new ArrayList<>();
        PreparedStatement statement = null;
        try{
            Connection connection = CustomConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SELECT_ALL_ACTIVE_USERS_QUERY);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                userList.add(extract(resultSet));
            }
            logger.log(Level.INFO,"List: " + userList);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return userList;
    }

    @Override
    public boolean blockUser(Long id) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = CustomConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(BLOCK_USER_QUERY);
            statement.setLong(1, id);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException("", e);
        }
    }

    @Override
    public boolean unblockUser(Long id) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = CustomConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(UNBLOCK_USER_QUERY);
            statement.setLong(1, id);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException("", e);
        }
    }

    @Override
    public User findByUserId(Long id) throws DaoException {
        User foundUser = null;
        try {
            Connection connection = CustomConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_USER_ID_QUERY);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                foundUser = extract(resultSet);
                System.out.println(foundUser);
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to find user: ", e);
        }
        logger.log(Level.DEBUG, "Found user: {}", foundUser);
        return foundUser;
    }

    @Override
    public User findByLogin(String login) throws DaoException {
        User foundUser = null;
        try {
            Connection connection = CustomConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_LOGIN_QUERY);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                foundUser = extract(resultSet);
                System.out.println(foundUser);
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to find user: ", e);
        }
        return foundUser;
    }

    @Override
    public User findByEmail(String email) throws DaoException {
        User foundUser = null;
        try {
            Connection connection = CustomConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_EMAIL_QUERY);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                foundUser = extract(resultSet);
                System.out.println(foundUser);
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to find user: ", e);
        }
        logger.log(Level.DEBUG, "Found user: {}", foundUser);
        return foundUser;
    }

    @Override
    public boolean update(User user) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = CustomConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(UPDATE_QUERY);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getLogin());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getEmail());
            statement.setBoolean(6, user.isBlocked());
            statement.setString(7, user.getUserStatus().getStatusName());
            statement.setString(8, user.getRole().getRoleName());
            statement.setLong(9, user.getUserId());
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException("Failed to find user: ", e);
        }
    }

    @Override
    public boolean updatePassword(Long userId, String newPassword) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = CustomConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(UPDATE_PASSWORD_QUERY);
            statement.setString(1, newPassword);
            statement.setLong(2, userId);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean updateUserStatus(Long userId, UserStatus status) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = CustomConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(UPDATE_USER_STATUS_QUERY);
            statement.setString(1, status.getStatusName());
            statement.setLong(2, userId);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean create(User user) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = CustomConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(INSERT_USER_QUERY);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getLogin());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getEmail());
            statement.setString(6, user.getUserStatus().getStatusName());
            statement.setString(7, user.getRole().getRoleName());
            statement.setInt(8, user.isBlocked() ? 1:0);
            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new DaoException("Failed to create user: ", e);
        }
    }

    @Override
    public boolean updateUserRole(Long id, UserRole role) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = CustomConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(UPDATE_USER_ROLE_QUERY);
            statement.setString(1, role.getRoleName());
            statement.setLong(2, id);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean deleteUser(Long id) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = CustomConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(DELETE_USER_QUERY);
            statement.setLong(1, id);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException("", e);
        }
    }

    private User extract(ResultSet resultSet) throws SQLException {
        return new User.Builder()
                .userId(resultSet.getLong("user_id"))
                .login(resultSet.getString("login"))
                .password(resultSet.getString("password"))
                .firstName(resultSet.getString("first_name"))
                .lastName(resultSet.getString("last_name"))
                .email(resultSet.getString("email"))
                .role(UserRole.valueOf(resultSet.getString("role").toUpperCase()))
                .userStatus(UserStatus.valueOf(resultSet.getString("status").toUpperCase()))
                .isBlocked(resultSet.getInt("is_blocked") == 1)
                .build();
    }
}
