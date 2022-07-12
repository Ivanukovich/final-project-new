package by.epam.bicyclerental.controller.command.impl.guest;

import by.epam.bicyclerental.controller.command.Command;
import by.epam.bicyclerental.controller.command.PagePath;
import by.epam.bicyclerental.controller.Router;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class LogOutCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        request.getSession().invalidate();
        return new Router(PagePath.HOME_PAGE, Router.RouterType.REDIRECT);
    }
}
