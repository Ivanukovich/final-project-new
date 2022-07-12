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
import java.util.Optional;

import static by.epam.bicyclerental.controller.command.PagePath.USER_EDIT_PAGE;
import static by.epam.bicyclerental.controller.command.Parameter.*;

public class FindUserCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final UserService userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        long userId = Integer.parseInt(request.getParameter(USER_ID));
        try {
            Optional<User> user = userService.findByUserId(userId);
            if (user.isPresent()) {
                request.setAttribute(USER, user.get());
            }
            return new Router(USER_EDIT_PAGE, Router.RouterType.FORWARD);
        } catch (ServiceException e) {
            throw new CommandException("Exception in FindUserCommand: ", e);
        }
    }
}
