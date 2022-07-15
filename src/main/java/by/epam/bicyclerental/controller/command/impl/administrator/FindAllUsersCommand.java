package by.epam.bicyclerental.controller.command.impl.administrator;

import by.epam.bicyclerental.controller.Router;
import by.epam.bicyclerental.controller.command.Command;
import by.epam.bicyclerental.exception.CommandException;
import by.epam.bicyclerental.exception.ServiceException;
import by.epam.bicyclerental.model.entity.User;
import by.epam.bicyclerental.model.service.UserService;
import by.epam.bicyclerental.model.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static by.epam.bicyclerental.controller.command.Parameter.USER_LIST;
import static by.epam.bicyclerental.controller.command.PagePath.USER_LIST_PAGE;

public class FindAllUsersCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    private static final UserService userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        try {
            List<User> listUsers = userService.findAllUsers();
            request.setAttribute(USER_LIST,listUsers);
            return new Router(USER_LIST_PAGE, Router.RouterType.FORWARD);
        } catch (ServiceException e) {
            throw new CommandException("Exception in FindAllUsersCommand: ", e);
        }
    }
}
