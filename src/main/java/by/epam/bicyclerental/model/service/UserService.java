package by.epam.bicyclerental.model.service;

import by.epam.bicyclerental.exception.DaoException;
import by.epam.bicyclerental.exception.ServiceException;
import by.epam.bicyclerental.model.entity.User;
import by.epam.bicyclerental.model.entity.UserRole;
import by.epam.bicyclerental.model.entity.UserStatus;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {
    Optional<User> logIn(String login, String password) throws ServiceException;
    boolean registerUser(Map<String, String> formData) throws ServiceException;
    List<User> findAllUsers() throws ServiceException;
    Optional<User> findByUserId(Long userId) throws ServiceException;
    Map<Long, String> findUserLogins() throws ServiceException;
    boolean blockUser(Long userId) throws ServiceException;
    boolean unblockUser(Long userId) throws ServiceException;
    boolean changePassword(Long userId, Map<String, String> formData) throws ServiceException;
    boolean updateUserRole(Long userId, UserRole role) throws ServiceException;
    boolean deleteUser(Long userId) throws ServiceException;
    boolean editUser(Long userId, Map<String, String> formData) throws ServiceException;
    boolean editProfile(Long userId, Map<String, String> formData) throws ServiceException;
}
