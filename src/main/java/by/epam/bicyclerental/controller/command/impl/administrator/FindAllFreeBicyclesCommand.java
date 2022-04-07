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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static by.epam.bicyclerental.controller.command.Literal.*;
import static by.epam.bicyclerental.controller.command.PagePath.FREE_BICYCLE_LIST;
import static by.epam.bicyclerental.controller.command.PagePath.RENTAL_POINT_LIST_PAGE;

public class FindAllFreeBicyclesCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final BicycleService bicycleService = BicycleServiceImpl.getInstance();
    private static final RentalPointService rentalPointService = RentalPointServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        long rental_point_id = Integer.parseInt(request.getParameter(Literal.RENTAL_POINT_ID));
        try {
            RentalPoint rentalPoint = rentalPointService.findRentalPointById(rental_point_id);
            List<Bicycle> bicycleList = bicycleService.findAllFreeBicyclesAtRentalPoint(rental_point_id);
            request.setAttribute(BICYCLE_LIST, bicycleList);
            request.setAttribute(RENTAL_POINT, rentalPoint);
        } catch (ServiceException e) {
            throw new CommandException("Exception in a FindAllRentalPoints class ", e);
        }
        return new Router(FREE_BICYCLE_LIST, Router.RouterType.FORWARD);
    }
}
