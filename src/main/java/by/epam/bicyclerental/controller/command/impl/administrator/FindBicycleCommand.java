package by.epam.bicyclerental.controller.command.impl.administrator;

import by.epam.bicyclerental.controller.Router;
import by.epam.bicyclerental.controller.command.Command;
import by.epam.bicyclerental.controller.command.Literal;
import by.epam.bicyclerental.exception.CommandException;
import by.epam.bicyclerental.exception.ServiceException;
import by.epam.bicyclerental.model.entity.Bicycle;
import by.epam.bicyclerental.model.entity.RentalPoint;
import by.epam.bicyclerental.model.service.BicycleService;
import by.epam.bicyclerental.model.service.RentalPointService;
import by.epam.bicyclerental.model.service.impl.BicycleServiceImpl;
import by.epam.bicyclerental.model.service.impl.RentalPointServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static by.epam.bicyclerental.controller.command.Literal.*;
import static by.epam.bicyclerental.controller.command.PagePath.RENT_BICYCLE_PAGE;

public class FindBicycleCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final BicycleService bicycleService = BicycleServiceImpl.getInstance();
    private static final RentalPointService rentalPointService = RentalPointServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        long bicycle_id = Integer.parseInt(request.getParameter(Literal.BICYCLE_ID));
        long rental_point_id = Integer.parseInt(request.getParameter(RENTAL_POINT_ID));
        try {
            RentalPoint rentalPoint = rentalPointService.findRentalPointById(rental_point_id);
            Bicycle bicycle = bicycleService.findBicycleById(bicycle_id);
            request.setAttribute(SELECTED_BICYCLE, bicycle);
            request.setAttribute(RENTAL_POINT, rentalPoint);
        } catch (ServiceException e) {
            throw new CommandException("Exception in a FindAllRentalPoints class ", e);
        }
        return new Router(RENT_BICYCLE_PAGE, Router.RouterType.FORWARD);
    }
}
