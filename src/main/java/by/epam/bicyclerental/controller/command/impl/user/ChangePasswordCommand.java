package by.epam.bicyclerental.controller.command.impl.user;

import by.epam.bicyclerental.controller.command.Command;
import by.epam.bicyclerental.controller.command.Literal;
import by.epam.bicyclerental.controller.command.PagePath;
import by.epam.bicyclerental.controller.Router;
import by.epam.bicyclerental.exception.CommandException;
import by.epam.bicyclerental.exception.ServiceException;
import by.epam.bicyclerental.model.service.UserService;
import by.epam.bicyclerental.model.service.impl.UserServiceImpl;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

public class ChangePasswordCommand implements Command {
    UserService service = new UserServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute(Literal.USER_ID);
        String oldPassword = (String) session.getAttribute(Literal.PASSWORD);
        String newPassword = (String) session.getAttribute(Literal.NEW_PASSWORD);
        Map<String, String> formData = new HashMap<>();
        formData.put(userId.toString(), Literal.USER_ID);
        formData.put(oldPassword, Literal.PASSWORD);
        formData.put(newPassword, Literal.NEW_PASSWORD);
        try {
            if(service.changePassword(formData)){
                return new Router(PagePath.USER_PAGE, Router.RouterType.REDIRECT);
            }
            else{
                for(String key: formData.keySet()){
                    String value = formData.get(key);
                    switch (value){

                    }
                }
            }
        }
        catch (ServiceException e){
            throw new CommandException("", e);
        }
        return null;
    }
}
