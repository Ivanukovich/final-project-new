package by.epam.bicyclerental.controller.command.impl.user;

import by.epam.bicyclerental.controller.command.Command;
import by.epam.bicyclerental.controller.command.Literal;
import by.epam.bicyclerental.controller.Router;
import by.epam.bicyclerental.controller.command.PagePath;
import by.epam.bicyclerental.exception.CommandException;
import by.epam.bicyclerental.exception.ServiceException;
import by.epam.bicyclerental.model.entity.Rent;
import by.epam.bicyclerental.model.entity.User;
import by.epam.bicyclerental.model.service.RentService;
import by.epam.bicyclerental.model.service.RentalPointService;
import by.epam.bicyclerental.model.service.UserService;
import by.epam.bicyclerental.model.service.impl.RentServiceImpl;
import by.epam.bicyclerental.model.service.impl.RentalPointServiceImpl;
import by.epam.bicyclerental.model.service.impl.UserServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Timestamp;

import static by.epam.bicyclerental.controller.command.Literal.USER_STATUS;

public class FinishRentCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final RentService rentService = new RentServiceImpl();
    private static final UserService userService = new UserServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        try {
            long user_id = Integer.parseInt(request.getParameter(Literal.USER_ID));
            User user = userService.findByUserId(user_id);
            Rent rent = rentService.findRentByUserId(user_id);
            rentService.finishRent(rent);
            session.setAttribute(USER_STATUS, user.getUserStatus());
            return new Router(PagePath.USER_PAGE, Router.RouterType.REDIRECT);
        }
        catch (ServiceException e){
            throw new CommandException("", e);
        }
    }
}
