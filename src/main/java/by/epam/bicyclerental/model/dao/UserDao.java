package by.epam.bicyclerental.model.dao;

import by.epam.bicyclerental.exception.DaoException;
import by.epam.bicyclerental.model.entity.User;
import by.epam.bicyclerental.model.entity.UserRole;
import by.epam.bicyclerental.model.entity.UserStatus;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    Optional<User> findByUserId(Long user_id) throws DaoException;
    Optional<User> findByLogin(String login) throws DaoException;
    Optional<User> findByEmail(String email) throws DaoException;
    List<User> findAllUsers() throws DaoException;
    List<User> findAllActiveUsers() throws DaoException;
    boolean blockUser(Long userId)  throws DaoException;
    boolean unblockUser (Long userId) throws DaoException;
    boolean update(User user) throws DaoException;
    boolean updatePassword(Long userId, String newPassword) throws DaoException;
    boolean updateUserStatus(Long userId, UserStatus status) throws DaoException;
    boolean create(User user) throws DaoException;
    boolean updateUserRole (Long userId, UserRole role) throws DaoException;
    boolean deleteUser(Long userId) throws DaoException;
}
