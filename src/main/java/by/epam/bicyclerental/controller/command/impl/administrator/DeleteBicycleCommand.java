package by.epam.bicyclerental.controller.command.impl.administrator;

import by.epam.bicyclerental.controller.Router;
import by.epam.bicyclerental.controller.command.Command;
import by.epam.bicyclerental.controller.command.Parameter;
import by.epam.bicyclerental.exception.CommandException;
import by.epam.bicyclerental.exception.ServiceException;
import by.epam.bicyclerental.model.entity.Bicycle;
import by.epam.bicyclerental.model.service.BicycleService;
import by.epam.bicyclerental.model.service.impl.BicycleServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.List;

import static by.epam.bicyclerental.controller.command.PagePath.BICYCLE_LIST_PAGE;
import static by.epam.bicyclerental.controller.command.Parameter.BICYCLE_LIST;

public class DeleteBicycleCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    private static final BicycleService bicycleService = BicycleServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        long bicycleId = Integer.parseInt(request.getParameter(Parameter.BICYCLE_ID));
        try {
            bicycleService.deleteBicycle(bicycleId);
            List<Bicycle> bicycleList = bicycleService.findAllBicycles();
            request.setAttribute(BICYCLE_LIST, bicycleList);
            return new Router(BICYCLE_LIST_PAGE, Router.RouterType.FORWARD);
        } catch (ServiceException e) {
            throw new CommandException("Exception in DeleteBicycleCommand: ", e);
        }
    }
}
