package by.epam.bicyclerental.controller.command.impl.administrator;

import by.epam.bicyclerental.controller.Router;
import by.epam.bicyclerental.controller.command.Command;
import by.epam.bicyclerental.controller.command.PagePath;
import by.epam.bicyclerental.exception.CommandException;
import by.epam.bicyclerental.exception.ServiceException;
import by.epam.bicyclerental.model.entity.RentalPoint;
import by.epam.bicyclerental.model.service.RentalPointService;
import by.epam.bicyclerental.model.service.impl.RentalPointServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.epam.bicyclerental.controller.command.Parameter.*;
import static by.epam.bicyclerental.controller.command.Parameter.USER;

public class EditRentalPointCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    private static final RentalPointService rentalPointService = RentalPointServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String location = request.getParameter(LOCATION);
        long rentalPointId = Integer.parseInt(request.getParameter(RENTAL_POINT_ID));
        try {
            rentalPointService.editRentalPoint(rentalPointId, location);
            List<RentalPoint> rentalPointList = rentalPointService.findAllRentalPoints();
            request.setAttribute(RENTAL_POINT_LIST, rentalPointList);
            return new Router(PagePath.RENTAL_POINT_LIST_PAGE, Router.RouterType.FORWARD);
        }
        catch (ServiceException e) {
            throw new CommandException("Exception in EditRentalPointCommand: ", e);
        }
    }
}
