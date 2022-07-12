package by.epam.bicyclerental.controller.command.impl.user;

import by.epam.bicyclerental.controller.Router;
import by.epam.bicyclerental.controller.command.Command;
import by.epam.bicyclerental.exception.CommandException;
import by.epam.bicyclerental.exception.ServiceException;
import by.epam.bicyclerental.model.entity.RentalPoint;
import by.epam.bicyclerental.model.service.BicycleService;
import by.epam.bicyclerental.model.service.RentalPointService;
import by.epam.bicyclerental.model.service.impl.BicycleServiceImpl;
import by.epam.bicyclerental.model.service.impl.RentalPointServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static by.epam.bicyclerental.controller.command.Parameter.*;
import static by.epam.bicyclerental.controller.command.PagePath.RENTAL_POINT_SELECT_PAGE;

public class FindAvailableRentalPointsCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final RentalPointService rentalPointService = RentalPointServiceImpl.getInstance();
    private static final BicycleService bicycleService = BicycleServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        try {
            List<RentalPoint> rentalPointList = rentalPointService.findAllRentalPoints();
            request.setAttribute(RENTAL_POINT_LIST, rentalPointList);
        } catch (ServiceException e) {
            throw new CommandException("Exception in a FindAllRentalPoints class ", e);
        }
        return new Router(RENTAL_POINT_SELECT_PAGE, Router.RouterType.FORWARD);
    }
}
