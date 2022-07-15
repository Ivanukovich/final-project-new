package by.epam.bicyclerental.controller.filter;

import by.epam.bicyclerental.controller.CommandType;
import by.epam.bicyclerental.controller.command.Command;
import by.epam.bicyclerental.controller.command.Parameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.epam.bicyclerental.controller.command.PagePath.*;

@WebFilter(urlPatterns = {"/*"})
public class ListPageFilter implements Filter {
    private static final Logger logger = LogManager.getLogger();

    private static final String PATH = "/bicyclerental_war_exploded/controller?command=";
    private static final String FIND_ALL_USERS_COMMAND = "find_all_bicycles";
    private static final String FIND_ALL_RENTAL_POINTS_COMMAND = "find_all_rental_points";
    private static final String FIND_ALL_BICYCLES_COMMAND = "find_all_bicycles";
    private static final String FIND_ALL_RENT_COMMAND = "find_all_rent_records";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestURI = request.getRequestURI();
        if(requestURI.contains(USER_LIST_PAGE)){
            response.sendRedirect(PATH + FIND_ALL_USERS_COMMAND);
            return;
        }
        if(requestURI.contains(BICYCLE_LIST_PAGE)){
            response.sendRedirect(PATH + FIND_ALL_BICYCLES_COMMAND);
            return;
        }
        if(requestURI.contains(RENTAL_POINT_LIST_PAGE)){
            response.sendRedirect(PATH + FIND_ALL_RENTAL_POINTS_COMMAND);
            return;
        }
        if(requestURI.contains(RENT_RECORD_LIST_PAGE)){
            response.sendRedirect(PATH + FIND_ALL_RENT_COMMAND);
            return;
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
