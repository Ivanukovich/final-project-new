package by.epam.bicyclerental.model.service.impl;

import by.epam.bicyclerental.controller.command.Parameter;
import by.epam.bicyclerental.exception.DaoException;
import by.epam.bicyclerental.exception.ServiceException;
import by.epam.bicyclerental.model.dao.RentRecordDao;
import by.epam.bicyclerental.model.dao.UserDao;
import by.epam.bicyclerental.model.dao.impl.RentRecordDaoImpl;
import by.epam.bicyclerental.model.dao.impl.UserDaoImpl;
import by.epam.bicyclerental.model.entity.User;
import by.epam.bicyclerental.model.entity.UserRole;
import by.epam.bicyclerental.model.entity.UserStatus;
import by.epam.bicyclerental.model.service.UserService;
import by.epam.bicyclerental.util.impl.ValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.epam.bicyclerental.controller.command.Parameter.*;

public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger();

    private static UserServiceImpl instance;
    private static final  UserDao userDao = UserDaoImpl.getInstance();
    private static final RentRecordDao rentDao = new RentRecordDaoImpl();

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }

    @Override
    public Optional<User> logIn(String login, String password) throws ServiceException {
        Optional<User> user;
        try {
            user = userDao.findByLogin(login);
            if (user.isPresent()) {
                if (user.get().getPassword().equals(password))
                {
                    return user;
                }
                else {
                    return Optional.empty();
                }
            }
            else {
                return Optional.empty();
            }
        }
        catch (DaoException e){
            throw new ServiceException("Exception in user service method logIn: ", e);
        }
    }

    @Override
    public boolean registerUser(Map<String, String> formData) throws ServiceException {
        try {
            boolean result = true;
            String firstName = formData.get(Parameter.FIRST_NAME);
            String lastName = formData.get(Parameter.LAST_NAME);
            String login = formData.get(Parameter.LOGIN);
            String email = formData.get(EMAIL);
            String password = formData.get(Parameter.PASSWORD);
            if (userDao.findByLogin(login).isPresent())
            {
                formData.put(LOGIN, LOGIN_ALREADY_EXISTS);
                result = false;
            }
            if (userDao.findByEmail(email).isPresent())
            {
                formData.put(EMAIL, EMAIL_ALREADY_EXISTS);
                result = false;
            }
            if (!ValidatorImpl.getInstance().validateName(firstName)){
                formData.put(FIRST_NAME, INVALID_FIRST_NAME);
                result = false;
            }
            if (!ValidatorImpl.getInstance().validateName(lastName)){
                formData.put(LAST_NAME, INVALID_LAST_NAME);
                result = false;
            }
            if (!ValidatorImpl.getInstance().validateLogin(login)){
                formData.put(LOGIN, INVALID_LOGIN);
                result = false;
            }
            if (!ValidatorImpl.getInstance().validateEmail(email)){
                formData.put(EMAIL, INVALID_EMAIL);
                result = false;
            }
            if (!ValidatorImpl.getInstance().validatePassword(password)){
                formData.put(PASSWORD, INVALID_PASSWORD);
                result = false;
            }
            if (result) {
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
                return userDao.create(user);
            }
            else {
                return false;
            }
        } catch (DaoException e) {
            throw new ServiceException("Exception in user service method registerUser: ", e);
        }
    }

    @Override
    public List<User> findAllUsers() throws ServiceException {
        try {
            return userDao.findAllUsers();
        } catch (DaoException e) {
            throw new ServiceException("Exception in user service method findAllUsers: ", e);
        }
    }

    @Override
    public Optional<User> findByUserId(Long userId) throws ServiceException {
        try {
            return userDao.findByUserId(userId);
        } catch (DaoException e) {
            throw new ServiceException("Exception in user service method findByUserId: ", e);
        }
    }

    @Override
    public Map<Long, String> findUserLogins() throws ServiceException {
        try {
            Map<Long, String> logins = new HashMap<>();
            List<User> users = userDao.findAllUsers();
            for (User user: users) {
                logins.put(user.getUserId(), user.getLogin());
            }
            return logins;
        } catch (DaoException e) {
            throw new ServiceException("Exception in user service method blockUser: ", e);
        }
    }

    @Override
    public boolean blockUser(Long id) throws ServiceException {
        try {
            return userDao.blockUser(id);
        } catch (DaoException e) {
            throw new ServiceException("Exception in user service method blockUser: ", e);
        }

    }

    @Override
    public boolean unblockUser(Long id) throws ServiceException {
        try {
            return userDao.unblockUser(id);
        } catch (DaoException e) {
            throw new ServiceException("Exception in user service method unblockUser: ", e);
        }

    }

    @Override
    public boolean changePassword(Long userId, Map<String, String> formData) throws ServiceException {
        String oldPassword = formData.get(PASSWORD);
        String newPassword = formData.get(NEW_PASSWORD);
        try {
            Optional<User> user = userDao.findByUserId(userId);
            if (user.isPresent()){
                if (!user.get().getPassword().equals(oldPassword)){
                    formData.put(PASSWORD, WRONG_PASSWORD);
                    return false;
                }
                if (oldPassword.equals(newPassword)) {
                    formData.put(NEW_PASSWORD, NEW_PASSWORD_EQUALS_OLD);
                    return false;
                }
                if (!ValidatorImpl.getInstance().validatePassword(newPassword)){
                    formData.put(NEW_PASSWORD, INVALID_PASSWORD);
                    return false;
                }
                return userDao.updatePassword(userId, newPassword);
            }
            else {
                return false;
            }
        }
        catch (DaoException e){
            throw new ServiceException("Exception in user service method changePassword: ", e);
        }
    }

    @Override
    public boolean updateUserRole(Long id, UserRole role) throws ServiceException {
        try {
            return userDao.updateUserRole(id, role);
        }
        catch (DaoException e){
            throw new ServiceException("Exception in user service method updateUserRole: ", e);
        }
    }

    @Override
    public boolean deleteUser(Long userId) throws ServiceException {
        try {
            rentDao.deleteRentByUserId(userId);
            return userDao.deleteUser(userId);
        } catch (DaoException e) {
            throw new ServiceException("Exception in user service method deleteUser: " + userId, e);
        }
    }

    @Override
    public boolean editUser(Long userId, Map<String, String> formData) throws ServiceException {
        try {
            Optional<User> user = userDao.findByUserId(userId);
            if (user.isPresent()) {
                User currentUser = user.get();
                String firstName = formData.get(Parameter.FIRST_NAME);
                String lastName = formData.get(Parameter.LAST_NAME);
                String login = formData.get(Parameter.LOGIN);
                String email = formData.get(EMAIL);
                String password = formData.get(Parameter.PASSWORD);
                boolean result = true;
                if (!login.equals(currentUser.getLogin())) {
                    if (userDao.findByLogin(login).isPresent()) {
                        formData.put(LOGIN, LOGIN_ALREADY_EXISTS);
                        result = false;
                    }
                    if (!ValidatorImpl.getInstance().validateLogin(login)) {
                        formData.put(LOGIN, INVALID_LOGIN);
                        result = false;
                    }
                }
                if (!email.equals(currentUser.getEmail())) {
                    if (userDao.findByEmail(email).isPresent()) {
                        formData.put(EMAIL, EMAIL_ALREADY_EXISTS);
                        result = false;
                    }
                    if (!ValidatorImpl.getInstance().validateEmail(email)) {
                        formData.put(EMAIL, INVALID_EMAIL);
                        result = false;
                    }
                }
                if (!ValidatorImpl.getInstance().validateName(firstName) && !firstName.equals(currentUser.getFirstName())) {
                    formData.put(FIRST_NAME, INVALID_FIRST_NAME);
                    result = false;
                }
                if (!ValidatorImpl.getInstance().validateName(lastName) && !lastName.equals(currentUser.getLastName())) {
                    formData.put(LAST_NAME, INVALID_LAST_NAME);
                    result = false;
                }

                if (!ValidatorImpl.getInstance().validatePassword(password) && !password.equals(currentUser.getPassword())) {
                    formData.put(PASSWORD, INVALID_PASSWORD);
                    result = false;
                }
                if (result) {
                    currentUser.setFirstName(firstName);
                    currentUser.setLastName(lastName);
                    currentUser.setLogin(login);
                    currentUser.setEmail(email);
                    currentUser.setPassword(password);
                    return userDao.update(currentUser);
                }
                else {
                    return false;
                }
            }
            return false;
        } catch (DaoException e) {
            throw new ServiceException("Exception in user service method editUser: ", e);
        }
    }

    @Override
    public boolean editProfile(Long userId, Map<String, String> formData) throws ServiceException {
        try {
            Optional<User> user = userDao.findByUserId(userId);
            if (user.isPresent()) {
                User currentUser = user.get();
                String firstName = formData.get(Parameter.FIRST_NAME);
                String lastName = formData.get(Parameter.LAST_NAME);
                String email = formData.get(EMAIL);
                boolean result = true;
                if (!email.equals(currentUser.getEmail())) {
                    if (userDao.findByEmail(email).isPresent()) {
                        formData.put(EMAIL, EMAIL_ALREADY_EXISTS);
                        result = false;
                    }
                    if (!ValidatorImpl.getInstance().validateEmail(email)) {
                        formData.put(EMAIL, INVALID_EMAIL);
                        result = false;
                    }
                }
                if (!ValidatorImpl.getInstance().validateName(firstName) && !firstName.equals(currentUser.getFirstName())) {
                    formData.put(FIRST_NAME, INVALID_FIRST_NAME);
                    result = false;
                }
                if (!ValidatorImpl.getInstance().validateName(lastName) && !lastName.equals(currentUser.getLastName())) {
                    formData.put(LAST_NAME, INVALID_LAST_NAME);
                    result = false;
                }
                if (result) {
                    currentUser.setFirstName(firstName);
                    currentUser.setLastName(lastName);
                    currentUser.setEmail(email);
                    return userDao.update(currentUser);
                }
                else {
                    return false;
                }
            }
            return false;
        } catch (DaoException e) {
            throw new ServiceException("Exception in user service method editProfile: ", e);
        }
    }
}
