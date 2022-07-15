package by.epam.bicyclerental.controller.command.impl.user;

import by.epam.bicyclerental.controller.Router;
import by.epam.bicyclerental.controller.command.Command;
import by.epam.bicyclerental.controller.command.Parameter;
import by.epam.bicyclerental.exception.CommandException;
import by.epam.bicyclerental.exception.ServiceException;
import by.epam.bicyclerental.model.entity.UserStatus;
import by.epam.bicyclerental.model.service.RentRecordService;
import by.epam.bicyclerental.model.service.UserService;
import by.epam.bicyclerental.model.service.impl.RentRecordServiceImpl;
import by.epam.bicyclerental.model.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static by.epam.bicyclerental.controller.command.Parameter.USER_STATUS;
import static by.epam.bicyclerental.controller.command.PagePath.USER_PAGE;

public class RentBicycleCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    private static final RentRecordService rentService = new RentRecordServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        long userId = Long.parseLong(request.getParameter(Parameter.USER_ID));
        long bicycleId = Long.parseLong(request.getParameter(Parameter.BICYCLE_ID));

        try{
            if (rentService.createRent(bicycleId, userId)){
                session.setAttribute(USER_STATUS, UserStatus.ACTIVE);
            }
            return new Router(USER_PAGE, Router.RouterType.REDIRECT);
        }

        catch (ServiceException e){
            throw new CommandException("Exception in RentBicycleCommand: ", e);
        }
    }
}
