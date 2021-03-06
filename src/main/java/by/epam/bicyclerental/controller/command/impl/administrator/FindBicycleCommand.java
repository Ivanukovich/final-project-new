package by.epam.bicyclerental.controller.command.impl.administrator;

import by.epam.bicyclerental.controller.Router;
import by.epam.bicyclerental.controller.command.Command;
import by.epam.bicyclerental.exception.CommandException;
import by.epam.bicyclerental.exception.ServiceException;
import by.epam.bicyclerental.model.entity.Bicycle;
import by.epam.bicyclerental.model.entity.User;
import by.epam.bicyclerental.model.service.BicycleService;
import by.epam.bicyclerental.model.service.UserService;
import by.epam.bicyclerental.model.service.impl.BicycleServiceImpl;
import by.epam.bicyclerental.model.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static by.epam.bicyclerental.controller.command.PagePath.BICYCLE_EDIT_PAGE;
import static by.epam.bicyclerental.controller.command.PagePath.USER_EDIT_PAGE;
import static by.epam.bicyclerental.controller.command.Parameter.*;

public class FindBicycleCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    private static final BicycleService bicycleService = BicycleServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        long userId = Integer.parseInt(request.getParameter(BICYCLE_ID));
        try {
            Optional<Bicycle> bicycle = bicycleService.findBicycleById(userId);
            if (bicycle.isPresent()) {
                request.setAttribute(BICYCLE, bicycle.get());
            }
            return new Router(BICYCLE_EDIT_PAGE, Router.RouterType.FORWARD);
        } catch (ServiceException e) {
            throw new CommandException("Exception in FindBicycleCommand: ", e);
        }
    }
}
