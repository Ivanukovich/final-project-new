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
import java.util.List;
import java.util.Optional;

import static by.epam.bicyclerental.controller.command.Parameter.*;
import static by.epam.bicyclerental.controller.command.PagePath.BICYCLE_SELECT_PAGE;

public class FindAvailableBicyclesCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final BicycleService bicycleService = BicycleServiceImpl.getInstance();
    private static final RentalPointService rentalPointService = RentalPointServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        long rentalPointId = Integer.parseInt(request.getParameter(Parameter.RENTAL_POINT_ID));
        try {
            Optional<RentalPoint> rentalPoint = rentalPointService.findRentalPointById(rentalPointId);
            List<Bicycle> bicycleList = bicycleService.findAllFreeBicyclesAtRentalPoint(rentalPointId);
            if (rentalPoint.isPresent()) {
                request.setAttribute(BICYCLE_LIST, bicycleList);
                request.setAttribute(RENTAL_POINT, rentalPoint.get());
            }
            return new Router(BICYCLE_SELECT_PAGE, Router.RouterType.FORWARD);
        } catch (ServiceException e) {
            throw new CommandException("Exception in a FindAllRentalPointsCommand class ", e);
        }
    }
}
