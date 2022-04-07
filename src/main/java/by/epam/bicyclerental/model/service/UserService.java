package by.epam.bicyclerental.model.service;

import by.epam.bicyclerental.exception.DaoException;
import by.epam.bicyclerental.exception.ServiceException;
import by.epam.bicyclerental.model.entity.User;
import by.epam.bicyclerental.model.entity.UserStatus;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {
    Optional<User> logIn(String login, String password) throws ServiceException;
    boolean registerUser(Map<String, String> formData) throws ServiceException;
    List<User> findAllUsers() throws ServiceException;
    User findByUserId(Long id) throws ServiceException;
    boolean blockUser(Long id) throws ServiceException;
    boolean unblockUser(Long id) throws ServiceException;
    boolean changePassword(Map<String, String> formData) throws ServiceException;
    boolean createAdministrator(Long id) throws ServiceException;
    boolean deleteUser(Long id) throws ServiceException;
}
