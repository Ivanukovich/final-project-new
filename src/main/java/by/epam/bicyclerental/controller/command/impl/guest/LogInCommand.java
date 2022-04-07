package by.epam.bicyclerental.controller.command.impl.guest;

import by.epam.bicyclerental.controller.command.Command;
import by.epam.bicyclerental.controller.command.Literal;
import by.epam.bicyclerental.controller.command.PagePath;
import by.epam.bicyclerental.controller.Router;
import by.epam.bicyclerental.exception.ServiceException;
import by.epam.bicyclerental.model.entity.User;
import by.epam.bicyclerental.model.service.UserService;
import by.epam.bicyclerental.model.service.impl.UserServiceImpl;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class LogInCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final UserService userService = UserServiceImpl.getInstance();
    private static final String INCORRECT_LOGIN_OR_PASSWORD = "incorrect_login_or_password";

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String login = request.getParameter(Literal.LOGIN);
        String password = request.getParameter(Literal.PASSWORD);
        try {
            Optional<User> optionalUser = userService.logIn(login, password);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                session.setAttribute(Literal.USER_ID, user.getUserId());
                session.setAttribute(Literal.FIRST_NAME, user.getFirstName());
                session.setAttribute(Literal.LAST_NAME, user.getLastName());
                session.setAttribute(Literal.LOGIN, user.getLogin());
                session.setAttribute(Literal.PASSWORD, user.getPassword());
                session.setAttribute(Literal.EMAIL, user.getEmail());
                session.setAttribute(Literal.USER_ROLE, user.getRole());
                session.setAttribute(Literal.USER_STATUS, user.getUserStatus());
                session.setAttribute(Literal.USER_BLOCKED, user.isBlocked());
                return new Router(PagePath.USER_PAGE, Router.RouterType.REDIRECT);
            }
            else {
                request.setAttribute(Literal.ERROR_MESSAGE, INCORRECT_LOGIN_OR_PASSWORD);
                return new Router(PagePath.LOG_IN_PAGE, Router.RouterType.FORWARD);
            }
        } catch (ServiceException e) {
            e.printStackTrace();
            return new Router(PagePath.ERROR_PAGE, Router.RouterType.REDIRECT);
        }
    }
}
