package by.epam.bicyclerental.controller.command.impl.guest;

import by.epam.bicyclerental.controller.command.Command;
import by.epam.bicyclerental.controller.command.Parameter;
import by.epam.bicyclerental.controller.command.PagePath;
import by.epam.bicyclerental.controller.Router;
import by.epam.bicyclerental.exception.ServiceException;
import by.epam.bicyclerental.model.entity.User;
import by.epam.bicyclerental.model.service.UserService;
import by.epam.bicyclerental.model.service.impl.UserServiceImpl;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static by.epam.bicyclerental.controller.command.Parameter.INCORRECT_LOGIN_OR_PASSWORD;
import static by.epam.bicyclerental.controller.command.Parameter.INCORRECT_LOGIN_OR_PASSWORD_MESSAGE;

public class LogInCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final UserService userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String login = request.getParameter(Parameter.LOGIN);
        String password = request.getParameter(Parameter.PASSWORD);
        try {
            Optional<User> optionalUser = userService.logIn(login, password);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                session.setAttribute(Parameter.USER_ID, user.getUserId());
                session.setAttribute(Parameter.FIRST_NAME, user.getFirstName());
                session.setAttribute(Parameter.LAST_NAME, user.getLastName());
                session.setAttribute(Parameter.LOGIN, user.getLogin());
                session.setAttribute(Parameter.PASSWORD, user.getPassword());
                session.setAttribute(Parameter.EMAIL, user.getEmail());
                session.setAttribute(Parameter.USER_ROLE, user.getRole());
                session.setAttribute(Parameter.USER_STATUS, user.getUserStatus());
                session.setAttribute(Parameter.USER_BLOCKED, user.isBlocked());
                return new Router(PagePath.USER_PAGE, Router.RouterType.REDIRECT);
            }
            else {
                request.setAttribute(INCORRECT_LOGIN_OR_PASSWORD, INCORRECT_LOGIN_OR_PASSWORD_MESSAGE);
                return new Router(PagePath.LOG_IN_PAGE, Router.RouterType.FORWARD);
            }
        } catch (ServiceException e) {
            e.printStackTrace();
            return new Router(PagePath.ERROR_500, Router.RouterType.REDIRECT);
        }
    }
}
