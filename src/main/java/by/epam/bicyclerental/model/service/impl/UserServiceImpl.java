package by.epam.bicyclerental.model.service.impl;

import by.epam.bicyclerental.controller.command.Literal;
import by.epam.bicyclerental.exception.DaoException;
import by.epam.bicyclerental.exception.ServiceException;
import by.epam.bicyclerental.model.dao.UserDao;
import by.epam.bicyclerental.model.dao.impl.UserDaoImpl;
import by.epam.bicyclerental.model.entity.User;
import by.epam.bicyclerental.model.entity.UserRole;
import by.epam.bicyclerental.model.entity.UserStatus;
import by.epam.bicyclerental.model.service.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.epam.bicyclerental.controller.command.Literal.LOGIN;
import static by.epam.bicyclerental.controller.command.Literal.PASSWORD;

public class UserServiceImpl implements UserService {
    private static UserServiceImpl instance;
    private static final Logger logger = LogManager.getLogger();
    private static final  UserDao userDao = UserDaoImpl.getInstance();

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }

    @Override
    public Optional<User> logIn(String login, String password) throws ServiceException {
        User user;
        try {
            user = userDao.findByLogin(login);
        }
        catch (DaoException e){
            throw new ServiceException("", e);
        }
        if (user == null) {
            return Optional.empty();
        }
        if (user.getPassword().equals(password))
        {
            return Optional.of(user);
        }
        else {
            return Optional.empty();
        }
    }

    @Override
    public boolean registerUser(Map<String, String> formData) throws ServiceException {
        try {
            boolean isValid = true;
            String firstName = formData.get(Literal.FIRST_NAME);
            String lastName = formData.get(Literal.LAST_NAME);
            String login = formData.get(Literal.LOGIN);
            if (userDao.findByLogin(login) != null)
            {
                formData.put(Literal.LOGIN, Literal.LOGIN_ALREADY_EXISTS);
                isValid = false;
            }

            String email = formData.get(Literal.EMAIL);
            if (userDao.findByEmail(email) != null)
            {
                formData.put(Literal.EMAIL, Literal.EMAIL_ALREADY_EXISTS);
                isValid = false;
            }

            String password = formData.get(Literal.PASSWORD);
            if(isValid) {
                User user = new User.Builder()
                        .firstName(firstName)
                        .lastName(lastName)
                        .login(login)
                        .password(password)
                        .email(email)
                        .userStatus(UserStatus.INACTIVE)
                        .role(UserRole.USER)
                        .isBlocked(false)
                        .build();
                boolean result = userDao.create(user);
                return result;
            }
            else {
                return false;
            }
        } catch (DaoException e) {
            throw new ServiceException("", e);
        }
    }

    @Override
    public List<User> findAllUsers() throws ServiceException {
        List<User> allUsers;
        try {
            allUsers = userDao.findAllUsers();
        } catch (DaoException e) {
            throw new ServiceException("", e);
        }

        logger.log(Level.DEBUG, "All users found: {}", allUsers);
        return allUsers;
    }

    @Override
    public User findByUserId(Long id) throws ServiceException {
        User user;
        try {
            user = userDao.findByUserId(id);
        } catch (DaoException e) {
            throw new ServiceException("findById() - Failed to find user by id " + id, e);
        }

        logger.log(Level.DEBUG, "Found user by id {}: {}", id, user);
        return user;
    }

    @Override
    public boolean blockUser(Long id) throws ServiceException {
        try {
            return userDao.blockUser(id);
        } catch (DaoException e) {
            throw new ServiceException("" + id, e);
        }

    }

    @Override
    public boolean unblockUser(Long id) throws ServiceException {
        try {
            return userDao.unblockUser(id);
        } catch (DaoException e) {
            throw new ServiceException("" + id, e);
        }

    }

    @Override
    public boolean changePassword(Map<String, String> formData) throws ServiceException {
        long userId = Long.parseLong(formData.get(Literal.USER_ID));
        String oldPassword = formData.get(Literal.PASSWORD);
        String newPassword = formData.get(Literal.NEW_PASSWORD);
        try {
            User user = userDao.findByUserId(userId);
            if (!user.getPassword().equals(oldPassword))
            {
                return false;
            }
            if (oldPassword.equals(newPassword))
            {
                return false;
            }
            return userDao.updatePassword(userId, newPassword);
        }
        catch (DaoException e){
            throw new ServiceException("", e);
        }
    }

    @Override
    public boolean createAdministrator(Long id) throws ServiceException {
        try {
            return userDao.updateUserRole(id, UserRole.ADMINISTRATOR);
        }
        catch (DaoException e){
            throw new ServiceException("", e);
        }
    }

    @Override
    public boolean deleteUser(Long id) throws ServiceException {
        try {
            return userDao.deleteUser(id);
        } catch (DaoException e) {
            throw new ServiceException("" + id, e);
        }
    }
}
