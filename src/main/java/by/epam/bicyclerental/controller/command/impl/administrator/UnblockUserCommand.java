package by.epam.bicyclerental.controller.command.impl.administrator;

import by.epam.bicyclerental.controller.command.Command;
import by.epam.bicyclerental.controller.command.Literal;
import by.epam.bicyclerental.controller.Router;
import by.epam.bicyclerental.exception.CommandException;
import by.epam.bicyclerental.exception.ServiceException;
import by.epam.bicyclerental.model.service.UserService;
import by.epam.bicyclerental.model.service.impl.UserServiceImpl;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static by.epam.bicyclerental.controller.command.Literal.CURRENT_PAGE;

public class UnblockUserCommand implements Command {
    public static final UserService userService = new UserServiceImpl();
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        long user_id = Integer.parseInt(request.getParameter(Literal.USER_ID));
        try {
            userService.unblockUser(user_id);

        } catch (ServiceException e) {
            throw new CommandException("", e);
        }
        HttpSession session = request.getSession();
        String currentPage = (String) session.getAttribute(CURRENT_PAGE);
        return new Router(currentPage, Router.RouterType.REDIRECT);
    }
}
