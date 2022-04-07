package by.epam.bicyclerental.controller;

import by.epam.bicyclerental.controller.command.Command;
import by.epam.bicyclerental.exception.CommandException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Optional;

import static by.epam.bicyclerental.controller.command.Literal.COMMAND;
import static by.epam.bicyclerental.controller.command.PagePath.ERROR_500;
import static by.epam.bicyclerental.controller.command.PagePath.USER_PAGE;

@WebServlet(urlPatterns = {"/controller"})
public class Controller extends HttpServlet {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.log(Level.DEBUG,"It's a " + request.getMethod());
        processRequest(request, response);
    }
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.log(Level.DEBUG,"It's a " + request.getMethod());
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String commandName = request.getParameter(COMMAND);
        logger.log(Level.DEBUG,"The command is " + commandName);
        Optional<Command> command = CommandType.provideCommand(commandName);
        try {
            Router router;
            if(command.isPresent()){
                router = command.get().execute(request);
                String page = router.getPagePath();
                if(router.getRouterType() == Router.RouterType.FORWARD){
                    logger.log(Level.INFO,"Forward type.");
                    request.getRequestDispatcher(page).forward(request,response);
                }else{
                    logger.log(Level.INFO,"Redirect type.");
                    response.sendRedirect(page);
                }
            }else {
                //response.sendRedirect(ERROR_500);
            }
        } catch (CommandException e) {
            logger.log(Level.ERROR,e.getMessage());
            response.sendRedirect(ERROR_500);
        }
    }
}
