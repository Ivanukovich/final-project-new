package by.epam.bicyclerental.controller.command.impl.administrator;

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
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static by.epam.bicyclerental.controller.command.PagePath.RENTAL_POINT_LIST_PAGE;
import static by.epam.bicyclerental.controller.command.Parameter.*;
import static by.epam.bicyclerental.controller.command.Parameter.INACTIVE_BICYCLES_LIST;

public class DeleteRentalPointCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    private static final RentalPointService rentalPointService = RentalPointServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        long rentalPointId = Integer.parseInt(request.getParameter(RENTAL_POINT_ID));
        try {
            rentalPointService.deleteRentalPoint(rentalPointId);
            List<RentalPoint> rentalPointList = rentalPointService.findAllRentalPoints();
            request.setAttribute(RENTAL_POINT_LIST, rentalPointList);
            return new Router(RENTAL_POINT_LIST_PAGE, Router.RouterType.FORWARD);
        } catch (ServiceException e) {
            throw new CommandException("Exception in DeleteRentalPointCommand: ", e);
        }
    }
}
