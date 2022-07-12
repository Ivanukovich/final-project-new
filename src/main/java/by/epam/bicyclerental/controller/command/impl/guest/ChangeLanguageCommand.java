package by.epam.bicyclerental.controller.command.impl.guest;

import by.epam.bicyclerental.controller.Router;
import by.epam.bicyclerental.controller.command.Command;
import by.epam.bicyclerental.controller.command.PagePath;
import by.epam.bicyclerental.exception.CommandException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.lang.reflect.Field;

import static by.epam.bicyclerental.controller.command.PagePath.*;
import static by.epam.bicyclerental.controller.command.Parameter.*;

public class ChangeLanguageCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String locale = request.getParameter(LOCALE);
        String currentPage = (String) session.getAttribute(CURRENT_PAGE);
        if (currentPage == null)
        {
            currentPage = HOME_PAGE;
        }
        session.setAttribute(LOCALE, locale);
        return new Router(currentPage, Router.RouterType.FORWARD);
    }
}
