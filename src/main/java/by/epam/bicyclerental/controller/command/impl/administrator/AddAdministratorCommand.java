package by.epam.bicyclerental.controller.command.impl.administrator;

import by.epam.bicyclerental.controller.command.Command;
import by.epam.bicyclerental.controller.command.Literal;
import by.epam.bicyclerental.controller.Router;
import by.epam.bicyclerental.exception.CommandException;
import by.epam.bicyclerental.exception.ServiceException;
import by.epam.bicyclerental.model.service.UserService;
import by.epam.bicyclerental.model.service.impl.UserServiceImpl;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.epam.bicyclerental.controller.command.PagePath.ADMINISTRATOR_PAGE;

public class AddAdministratorCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final UserService userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        long userId = Long.parseLong(request.getParameter(Literal.USER_ID));
        try{
            userService.createAdministrator(userId);
        }
        catch (ServiceException e){
            throw new CommandException("", e);
        }
        return new Router(ADMINISTRATOR_PAGE, Router.RouterType.REDIRECT);
    }
}
