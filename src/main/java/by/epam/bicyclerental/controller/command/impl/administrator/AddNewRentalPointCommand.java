package by.epam.bicyclerental.controller.command.impl.administrator;

import by.epam.bicyclerental.controller.command.Command;
import by.epam.bicyclerental.controller.command.Literal;
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

public class AddNewRentalPointCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final RentalPointService rentalPointService = RentalPointServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String location = request.getParameter(Literal.RENTAL_POINT_LOCATION);
        RentalPoint rentalPoint = new RentalPoint.Builder()
                .location(location)
                .build();
        try {
            rentalPointService.createRentalPoint(rentalPoint);
        }
        catch (ServiceException e){
            throw new CommandException("", e);
        }
        return new Router(PagePath.ADMINISTRATOR_PAGE, Router.RouterType.REDIRECT);
    }
}
