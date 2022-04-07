package by.epam.bicyclerental.controller.command.impl.administrator;

import by.epam.bicyclerental.controller.Router;
import by.epam.bicyclerental.controller.command.Command;
import by.epam.bicyclerental.controller.command.Literal;
import by.epam.bicyclerental.exception.CommandException;
import by.epam.bicyclerental.exception.ServiceException;
import by.epam.bicyclerental.model.service.BicycleService;
import by.epam.bicyclerental.model.service.impl.BicycleServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static by.epam.bicyclerental.controller.command.Literal.CURRENT_PAGE;
import static by.epam.bicyclerental.controller.command.PagePath.ADMINISTRATOR_PAGE;

public class RemoveBicycleCommand implements Command {
    private static final BicycleService bicycleService = BicycleServiceImpl.getInstance();
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        long bicycle_id = Integer.parseInt(request.getParameter(Literal.BICYCLE_ID));
        try {
            bicycleService.removeBicycle(bicycle_id);
        } catch (ServiceException e) {
            throw new CommandException("", e);
        }
        return new Router(ADMINISTRATOR_PAGE, Router.RouterType.REDIRECT);
    }
}
