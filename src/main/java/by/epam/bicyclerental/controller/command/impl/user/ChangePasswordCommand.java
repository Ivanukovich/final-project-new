package by.epam.bicyclerental.controller.command.impl.user;

import by.epam.bicyclerental.controller.command.Command;
import by.epam.bicyclerental.controller.command.Parameter;
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

import java.util.HashMap;
import java.util.Map;

import static by.epam.bicyclerental.controller.command.Parameter.*;

public class ChangePasswordCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    
    UserService userService = new UserServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        Long userId = (Long)session.getAttribute(Parameter.USER_ID);
        String oldPassword = request.getParameter(Parameter.PASSWORD);
        String newPassword = request.getParameter(Parameter.NEW_PASSWORD);
        Map<String, String> formData = new HashMap<>();
        formData.put(Parameter.PASSWORD, oldPassword);
        formData.put(Parameter.NEW_PASSWORD, newPassword);
        try {
            if(userService.changePassword(userId, formData)){
                return new Router(PagePath.USER_PROFILE_PAGE, Router.RouterType.REDIRECT);
            }
            else{
                for(String key: formData.keySet()){
                    String value = formData.get(key);
                    switch (value){
                        case NEW_PASSWORD_EQUALS_OLD:
                            request.setAttribute(NEW_PASSWORD_EQUALS_OLD, NEW_PASSWORD_EQUALS_OLD_MESSAGE);
                            break;
                        case WRONG_PASSWORD:
                            request.setAttribute(WRONG_PASSWORD, WRONG_PASSWORD_MESSAGE);
                            break;
                        case INVALID_PASSWORD:
                            request.setAttribute(INVALID_PASSWORD, INVALID_PASSWORD_MESSAGE);
                            break;
                    }
                }
                return new Router(PagePath.CHANGE_PASSWORD_PAGE, Router.RouterType.FORWARD);
            }
        }
        catch (ServiceException e){
            throw new CommandException("", e);
        }
    }
}
