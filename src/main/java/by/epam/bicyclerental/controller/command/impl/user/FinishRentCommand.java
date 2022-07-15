package by.epam.bicyclerental.controller.command.impl.user;

import by.epam.bicyclerental.controller.command.Command;
import by.epam.bicyclerental.controller.command.Parameter;
import by.epam.bicyclerental.controller.Router;
import by.epam.bicyclerental.controller.command.PagePath;
import by.epam.bicyclerental.exception.CommandException;
import by.epam.bicyclerental.exception.ServiceException;
import by.epam.bicyclerental.model.entity.RentRecord;
import by.epam.bicyclerental.model.entity.User;
import by.epam.bicyclerental.model.entity.UserStatus;
import by.epam.bicyclerental.model.service.RentRecordService;
import by.epam.bicyclerental.model.service.UserService;
import by.epam.bicyclerental.model.service.impl.RentRecordServiceImpl;
import by.epam.bicyclerental.model.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

import static by.epam.bicyclerental.controller.command.Parameter.USER_STATUS;

public class FinishRentCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    private static final RentRecordService rentService = new RentRecordServiceImpl();
    private static final UserService userService = new UserServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        try {
            long userId = Integer.parseInt(request.getParameter(Parameter.USER_ID));
            Optional<User> user = userService.findByUserId(userId);
            Optional<RentRecord> rentRecord = rentService.findRentByUserId(userId);
            if (user.isPresent() && rentRecord.isPresent()) {
                rentService.finishRent(rentRecord.get());
                session.setAttribute(USER_STATUS, UserStatus.INACTIVE);
            }
            return new Router(PagePath.USER_PAGE, Router.RouterType.REDIRECT);
        }
        catch (ServiceException e){
            throw new CommandException("Exception in FinishRentCommand: ", e);
        }
    }
}
