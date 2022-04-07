package by.epam.bicyclerental.model.dao;

import by.epam.bicyclerental.exception.DaoException;
import by.epam.bicyclerental.model.entity.User;
import by.epam.bicyclerental.model.entity.UserRole;
import by.epam.bicyclerental.model.entity.UserStatus;

import java.util.List;

public interface UserDao {
    User findByUserId(Long user_id) throws DaoException;
    User findByLogin(String login) throws DaoException;
    User findByEmail(String email) throws DaoException;
    List<User> findAllUsers() throws DaoException;
    List<User> findAllActiveUsers() throws DaoException;
    boolean blockUser(Long user_id)  throws DaoException;
    boolean unblockUser (Long user_id) throws DaoException;
    boolean update(User user) throws DaoException;
    boolean updatePassword(Long user_id, String newPassword) throws DaoException;
    boolean updateUserStatus(Long user_id, UserStatus status) throws DaoException;
    boolean create(User user) throws DaoException;
    boolean updateUserRole (Long user_id, UserRole role) throws DaoException;
    boolean deleteUser(Long user_id) throws DaoException;
}
