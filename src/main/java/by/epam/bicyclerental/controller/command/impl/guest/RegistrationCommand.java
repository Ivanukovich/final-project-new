package by.epam.bicyclerental.controller.command.impl.guest;

import by.epam.bicyclerental.controller.command.Command;
import by.epam.bicyclerental.controller.command.Literal;
import by.epam.bicyclerental.controller.command.PagePath;
import by.epam.bicyclerental.controller.Router;
import by.epam.bicyclerental.exception.CommandException;
import by.epam.bicyclerental.exception.ServiceException;
import by.epam.bicyclerental.model.service.UserService;
import by.epam.bicyclerental.model.service.impl.UserServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

public class RegistrationCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final UserService userService = UserServiceImpl.getInstance();
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Map<String, String> formData = new HashMap<>();
        Router router;
        formData.put(Literal.FIRST_NAME, request.getParameter(Literal.FIRST_NAME));
        formData.put(Literal.LAST_NAME, request.getParameter(Literal.LAST_NAME));
        formData.put(Literal.LOGIN, request.getParameter(Literal.LOGIN));
        formData.put(Literal.PASSWORD, request.getParameter(Literal.PASSWORD));
        formData.put(Literal.EMAIL, request.getParameter(Literal.EMAIL));
        try {
            if (userService.registerUser(formData)) {
                router = new Router(PagePath.USER_PAGE, Router.RouterType.REDIRECT);
            } else {
                router = new Router(PagePath.REGISTRATION_PAGE, Router.RouterType.FORWARD);
            }
        }
        catch (ServiceException e) {
            throw new CommandException("Error while registration command", e);
        }
        return router;
    }
}
