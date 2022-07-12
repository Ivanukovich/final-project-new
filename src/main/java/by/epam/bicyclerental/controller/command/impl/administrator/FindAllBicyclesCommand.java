package by.epam.bicyclerental.controller.command.impl.administrator;

import by.epam.bicyclerental.controller.Router;
import by.epam.bicyclerental.controller.command.Command;
import by.epam.bicyclerental.exception.CommandException;
import by.epam.bicyclerental.exception.ServiceException;
import by.epam.bicyclerental.model.entity.Bicycle;
import by.epam.bicyclerental.model.entity.RentalPoint;
import by.epam.bicyclerental.model.service.BicycleService;
import by.epam.bicyclerental.model.service.RentalPointService;
import by.epam.bicyclerental.model.service.impl.BicycleServiceImpl;
import by.epam.bicyclerental.model.service.impl.RentalPointServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static by.epam.bicyclerental.controller.command.PagePath.BICYCLE_LIST_PAGE;
import static by.epam.bicyclerental.controller.command.Parameter.BICYCLE_LIST;

public class FindAllBicyclesCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final BicycleService bicycleService = BicycleServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        try {
            List<Bicycle> bicycleList = bicycleService.findAllBicycles();
            request.setAttribute(BICYCLE_LIST, bicycleList);
            return new Router(BICYCLE_LIST_PAGE, Router.RouterType.FORWARD);
        } catch (ServiceException e) {
            throw new CommandException("Exception in FindAllBicyclesCommand: ", e);
        }
    }
}
