package by.epam.bicyclerental.controller.command.impl.administrator;

import by.epam.bicyclerental.controller.command.Command;
import by.epam.bicyclerental.controller.Router;
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
import java.util.Optional;

import static by.epam.bicyclerental.controller.command.PagePath.RENTAL_POINT_EDIT_PAGE;
import static by.epam.bicyclerental.controller.command.Parameter.*;

public class AddBicycleToRentalPointCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final BicycleService bicycleService = BicycleServiceImpl.getInstance();
    private static final RentalPointService rentalPointService = RentalPointServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        long bicycleId = Long.parseLong(request.getParameter(BICYCLE_ID));
        long rentalPointId = Long.parseLong(request.getParameter(RENTAL_POINT_ID));
        try {
            rentalPointService.addBicycleToRentalPoint(bicycleId, rentalPointId);
            Optional<RentalPoint> rentalPoint = rentalPointService.findRentalPointById(rentalPointId);
            if (rentalPoint.isPresent()) {
                request.setAttribute(RENTAL_POINT, rentalPoint.get());
                List<Bicycle> rentalPointbicycleList = bicycleService.findAllBicyclesAtRentalPoint(rentalPointId);
                List<Bicycle> inactiveBicycleList = bicycleService.findAllInactiveBicycles();
                request.setAttribute(RENTAL_POINT_BICYCLES_LIST, rentalPointbicycleList);
                request.setAttribute(INACTIVE_BICYCLES_LIST, inactiveBicycleList);
            }
            return new Router(RENTAL_POINT_EDIT_PAGE, Router.RouterType.FORWARD);
        }
        catch (ServiceException e) {
            throw new CommandException("Exception in AddBicycleToRentalPointCommand: ", e);
        }
    }
}
