package by.epam.bicyclerental.controller.command.impl.administrator;

import by.epam.bicyclerental.controller.command.Command;
import by.epam.bicyclerental.controller.command.Parameter;
import by.epam.bicyclerental.controller.command.PagePath;
import by.epam.bicyclerental.controller.Router;
import by.epam.bicyclerental.exception.CommandException;
import by.epam.bicyclerental.exception.ServiceException;
import by.epam.bicyclerental.model.entity.RentalPoint;
import by.epam.bicyclerental.model.service.RentalPointService;
import by.epam.bicyclerental.model.service.impl.RentalPointServiceImpl;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static by.epam.bicyclerental.controller.command.Parameter.RENTAL_POINT_LIST;

public class AddNewRentalPointCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final RentalPointService rentalPointService = RentalPointServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String location = request.getParameter(Parameter.RENTAL_POINT_LOCATION);
        RentalPoint rentalPoint = new RentalPoint.Builder()
                .location(location)
                .build();
        try {
            rentalPointService.createRentalPoint(rentalPoint);
            List<RentalPoint> rentalPointList = rentalPointService.findAllRentalPoints();
            request.setAttribute(RENTAL_POINT_LIST, rentalPointList);
            return new Router(PagePath.RENTAL_POINT_LIST_PAGE, Router.RouterType.FORWARD);
        }
        catch (ServiceException e){
            throw new CommandException("Exception in AddNewRentalPointCommand: ", e);
        }
    }
}
