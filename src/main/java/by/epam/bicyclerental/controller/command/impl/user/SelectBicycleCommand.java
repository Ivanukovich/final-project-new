package by.epam.bicyclerental.controller.command.impl.user;

import by.epam.bicyclerental.controller.Router;
import by.epam.bicyclerental.controller.command.Command;
import by.epam.bicyclerental.controller.command.Parameter;
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

import java.util.Optional;

import static by.epam.bicyclerental.controller.command.Parameter.*;
import static by.epam.bicyclerental.controller.command.PagePath.RENT_CONFIRMATION_PAGE;

public class SelectBicycleCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final BicycleService bicycleService = BicycleServiceImpl.getInstance();
    private static final RentalPointService rentalPointService = RentalPointServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        long bicycleId = Integer.parseInt(request.getParameter(Parameter.BICYCLE_ID));
        long rentalPointId = Integer.parseInt(request.getParameter(RENTAL_POINT_ID));
        try {
            Optional<RentalPoint> rentalPoint = rentalPointService.findRentalPointById(rentalPointId);
            Optional<Bicycle> bicycle = bicycleService.findBicycleById(bicycleId);
            if (rentalPoint.isPresent() && bicycle.isPresent()) {
                request.setAttribute(SELECTED_BICYCLE, bicycle.get());
                request.setAttribute(RENTAL_POINT, rentalPoint.get());
            }
            return new Router(RENT_CONFIRMATION_PAGE, Router.RouterType.FORWARD);
        } catch (ServiceException e) {
            throw new CommandException("Exception in a FindAllRentalPoints class ", e);
        }
    }
}
