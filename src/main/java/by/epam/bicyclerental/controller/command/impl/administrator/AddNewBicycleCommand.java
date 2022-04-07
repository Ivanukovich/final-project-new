package by.epam.bicyclerental.controller.command.impl.administrator;

import by.epam.bicyclerental.controller.command.Command;
import by.epam.bicyclerental.controller.command.Literal;
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

import java.util.HashMap;
import java.util.Map;

public class AddNewBicycleCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final BicycleService bicycleService = BicycleServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String model = request.getParameter(Literal.BICYCLE_MODEL);
        String status = request.getParameter(Literal.BICYCLE_STATUS);
        Bicycle bicycle = new Bicycle.Builder()
                .model(model)
                .status(BicycleStatus.valueOf(status))
                .build();
        try {
            bicycleService.addBicycletoDataBase(bicycle);
        }
        catch (ServiceException e){
            throw new CommandException("", e);
        }
        return new Router(PagePath.ADMINISTRATOR_PAGE, Router.RouterType.REDIRECT);
    }
}
