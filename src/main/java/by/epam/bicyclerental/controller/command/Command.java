package by.epam.bicyclerental.controller.command;

import by.epam.bicyclerental.controller.Router;
import by.epam.bicyclerental.exception.CommandException;
import javax.servlet.http.HttpServletRequest;

public interface Command {
    Router execute(HttpServletRequest request) throws CommandException;
}
