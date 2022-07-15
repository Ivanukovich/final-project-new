package by.epam.bicyclerental.controller.command.impl.administrator;

import by.epam.bicyclerental.controller.Router;
import by.epam.bicyclerental.controller.command.Command;
import by.epam.bicyclerental.controller.command.PagePath;
import by.epam.bicyclerental.exception.CommandException;
import by.epam.bicyclerental.exception.ServiceException;
import by.epam.bicyclerental.model.entity.Bicycle;
import by.epam.bicyclerental.model.entity.BicycleStatus;
import by.epam.bicyclerental.model.entity.RentalPoint;
import by.epam.bicyclerental.model.service.BicycleService;
import by.epam.bicyclerental.model.service.impl.BicycleServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static by.epam.bicyclerental.controller.command.Parameter.*;

public class EditBicycleCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    private static final BicycleService bicycleService = BicycleServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String model = request.getParameter(BICYCLE_MODEL);
        long bicycleId = Integer.parseInt(request.getParameter(BICYCLE_ID));
        try {
            bicycleService.updateBicycle(bicycleId, model);
            List<Bicycle> bicycleList = bicycleService.findAllBicycles();
            request.setAttribute(BICYCLE_LIST, bicycleList);
            return new Router(PagePath.BICYCLE_LIST_PAGE, Router.RouterType.FORWARD);
        }
        catch (ServiceException e) {
            throw new CommandException("Exception in EditBicycleCommand: ", e);
        }
    }
}
