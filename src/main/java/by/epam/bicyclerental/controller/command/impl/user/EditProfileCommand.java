package by.epam.bicyclerental.controller.command.impl.user;

import by.epam.bicyclerental.controller.Router;
import by.epam.bicyclerental.controller.command.Command;
import by.epam.bicyclerental.controller.command.PagePath;
import by.epam.bicyclerental.exception.CommandException;
import by.epam.bicyclerental.exception.ServiceException;
import by.epam.bicyclerental.model.service.UserService;
import by.epam.bicyclerental.model.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

import static by.epam.bicyclerental.controller.command.Parameter.*;

public class EditProfileCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    private static final UserService userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Map<String, String> formData = new HashMap<>();
        HttpSession session = request.getSession();
        formData.put(FIRST_NAME, request.getParameter(FIRST_NAME));
        formData.put(LAST_NAME, request.getParameter(LAST_NAME));
        formData.put(EMAIL, request.getParameter(EMAIL));
        long userId = (long)session.getAttribute(USER_ID);
        try {
            if (!userService.editProfile(userId, formData)) {
                for (String key : formData.keySet()) {
                    String currentValue = formData.get(key);
                    switch (currentValue) {
                        case EMAIL_ALREADY_EXISTS:
                            request.setAttribute(EMAIL_ALREADY_EXISTS, EMAIL_ALREADY_EXISTS_MESSAGE);
                            break;
                        case INVALID_FIRST_NAME:
                            request.setAttribute(INVALID_FIRST_NAME, INVALID_FIRST_NAME_MESSAGE);
                            break;
                        case INVALID_LAST_NAME:
                            request.setAttribute(INVALID_LAST_NAME, INVALID_LAST_NAME_MESSAGE);
                            break;
                        case INVALID_EMAIL:
                            request.setAttribute(INVALID_EMAIL, INVALID_EMAIL_MESSAGE);
                            break;
                    }
                }
            }
            return new Router(PagePath.USER_PROFILE_PAGE, Router.RouterType.FORWARD);
        }
        catch (ServiceException e) {
            throw new CommandException("Exception in EditUserCommand: ", e);
        }
    }
}
