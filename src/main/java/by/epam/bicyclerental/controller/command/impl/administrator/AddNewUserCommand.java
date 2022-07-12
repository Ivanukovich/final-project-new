package by.epam.bicyclerental.controller.command.impl.administrator;

import by.epam.bicyclerental.controller.Router;
import by.epam.bicyclerental.controller.command.Command;
import by.epam.bicyclerental.controller.command.Parameter;
import by.epam.bicyclerental.controller.command.PagePath;
import by.epam.bicyclerental.exception.CommandException;
import by.epam.bicyclerental.exception.ServiceException;
import by.epam.bicyclerental.model.entity.User;
import by.epam.bicyclerental.model.service.UserService;
import by.epam.bicyclerental.model.service.impl.UserServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.epam.bicyclerental.controller.command.PagePath.USER_LIST_PAGE;
import static by.epam.bicyclerental.controller.command.Parameter.*;

public class AddNewUserCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final UserService userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Map<String, String> formData = new HashMap<>();
        Router router;
        formData.put(Parameter.FIRST_NAME, request.getParameter(Parameter.FIRST_NAME));
        formData.put(Parameter.LAST_NAME, request.getParameter(Parameter.LAST_NAME));
        formData.put(Parameter.LOGIN, request.getParameter(Parameter.LOGIN));
        formData.put(Parameter.PASSWORD, request.getParameter(Parameter.PASSWORD));
        formData.put(Parameter.EMAIL, request.getParameter(Parameter.EMAIL));
        try {
            if (userService.registerUser(formData)) {
                List<User> listUsers = userService.findAllUsers();
                request.setAttribute(USER_LIST,listUsers);
                return new Router(USER_LIST_PAGE, Router.RouterType.FORWARD);
            } else {
                for (String key : formData.keySet()) {
                    String currentValue = formData.get(key);
                    switch (currentValue) {
                        case LOGIN_ALREADY_EXISTS:
                            request.setAttribute(LOGIN_ALREADY_EXISTS, LOGIN_ALREADY_EXISTS_MESSAGE);
                            break;
                        case EMAIL_ALREADY_EXISTS:
                            request.setAttribute(EMAIL_ALREADY_EXISTS, EMAIL_ALREADY_EXISTS_MESSAGE);
                            break;
                        case INVALID_FIRST_NAME:
                            request.setAttribute(INVALID_FIRST_NAME, INVALID_FIRST_NAME_MESSAGE);
                            break;
                        case INVALID_LAST_NAME:
                            request.setAttribute(INVALID_LAST_NAME, INVALID_LAST_NAME_MESSAGE);
                            break;
                        case INVALID_LOGIN:
                            request.setAttribute(INVALID_LOGIN, INVALID_LOGIN_MESSAGE);
                            break;
                        case INVALID_EMAIL:
                            request.setAttribute(INVALID_EMAIL, INVALID_EMAIL_MESSAGE);
                            break;
                        case INVALID_PASSWORD:
                            request.setAttribute(INVALID_PASSWORD, INVALID_PASSWORD_MESSAGE);
                            break;
                    }
                }
                router = new Router(PagePath.USER_CREATION_PAGE, Router.RouterType.FORWARD);
            }
            return router;
        }
        catch (ServiceException e) {
            throw new CommandException("Exception in AddNewUserCommand: ", e);
        }
    }
}
