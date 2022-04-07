package by.epam.bicyclerental.controller.command.impl.administrator;

import by.epam.bicyclerental.controller.command.Command;
import by.epam.bicyclerental.controller.command.Literal;
import by.epam.bicyclerental.controller.command.PagePath;
import by.epam.bicyclerental.controller.Router;
import by.epam.bicyclerental.exception.CommandException;
import by.epam.bicyclerental.exception.ServiceException;
import by.epam.bicyclerental.model.service.UserService;
import by.epam.bicyclerental.model.service.impl.UserServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static by.epam.bicyclerental.controller.command.Literal.CURRENT_PAGE;
import static by.epam.bicyclerental.controller.command.PagePath.ADMINISTRATOR_PAGE;

public class BlockUserCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final UserService service = UserServiceImpl.getInstance();
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        long user_id = Integer.parseInt(request.getParameter(Literal.USER_ID));
        HttpSession session = request.getSession();
        try {
            service.blockUser(user_id);
        } catch (ServiceException e) {
            throw new CommandException("", e);
        }
        String currentPage = (String) session.getAttribute(CURRENT_PAGE);
        return new Router(currentPage, Router.RouterType.REDIRECT);
    }
}
