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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static by.epam.bicyclerental.controller.command.Literal.*;
import static by.epam.bicyclerental.controller.command.PagePath.RENTAL_POINT_EDIT_PAGE;

public class FindAllRentalPointsWithBicyclesCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final RentalPointService rentalPointService = RentalPointServiceImpl.getInstance();
    private static final BicycleService bicycleService = BicycleServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        try {
            List<RentalPoint> rentalPointList = rentalPointService.findAllRentalPoints();
            List<Bicycle> inactiveBicycleList = bicycleService.findAllInactiveBicycles();
            Map<Long, List<Bicycle>> rentalPointBicycles = new HashMap<>();
            long rental_point_id;
            for (RentalPoint rentalPoint : rentalPointList){
                rental_point_id = rentalPoint.getRentalPointId();
                rentalPointBicycles.put(rental_point_id, bicycleService.findAllFreeBicyclesAtRentalPoint(rental_point_id));
            }
            request.setAttribute(RENTAL_POINT_LIST, rentalPointList);
            request.setAttribute(RENTAL_POINT_BICYCLES_LIST, rentalPointBicycles);
            request.setAttribute(INACTIVE_BICYCLES_LIST, inactiveBicycleList);
        } catch (ServiceException e) {
            throw new CommandException("Exception in a FindAllRentalPoints class ", e);
        }
        return new Router(RENTAL_POINT_EDIT_PAGE, Router.RouterType.FORWARD);
    }
}
