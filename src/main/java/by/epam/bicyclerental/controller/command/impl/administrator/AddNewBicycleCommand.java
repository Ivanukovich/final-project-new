package by.epam.bicyclerental.controller.command.impl.administrator;

import by.epam.bicyclerental.controller.command.Command;
import by.epam.bicyclerental.controller.command.Parameter;
import by.epam.bicyclerental.controller.command.PagePath;
import by.epam.bicyclerental.controller.Router;
import by.epam.bicyclerental.exception.CommandException;
import by.epam.bicyclerental.exception.ServiceException;
import by.epam.bicyclerental.model.entity.Bicycle;
import by.epam.bicyclerental.model.entity.BicycleStatus;
import by.epam.bicyclerental.model.service.BicycleService;
import by.epam.bicyclerental.model.service.impl.BicycleServiceImpl;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static by.epam.bicyclerental.controller.command.Parameter.BICYCLE_LIST;

public class AddNewBicycleCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final BicycleService bicycleService = BicycleServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String model = request.getParameter(Parameter.BICYCLE_MODEL);
        Bicycle bicycle = new Bicycle.Builder()
                .model(model)
                .status(BicycleStatus.FREE)
                .build();
        try {
            bicycleService.addBicycletoDataBase(bicycle);
            List<Bicycle> bicycleList = bicycleService.findAllBicycles();
            request.setAttribute(BICYCLE_LIST, bicycleList);
            return new Router(PagePath.BICYCLE_LIST_PAGE, Router.RouterType.FORWARD);
        }
        catch (ServiceException e){
            throw new CommandException("Exception in AddNewBicycleCommand: ", e);
        }
    }
}
