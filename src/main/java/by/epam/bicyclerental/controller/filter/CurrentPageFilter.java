package by.epam.bicyclerental.controller.filter;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static by.epam.bicyclerental.controller.command.Parameter.COMMAND;
import static by.epam.bicyclerental.controller.command.Parameter.CURRENT_PAGE;

@WebFilter(urlPatterns = {"/*"})
public class CurrentPageFilter implements Filter {
    private static final Logger logger = LogManager.getLogger();

    private static final String CONTROLLER = "/controller?";
    private static final String JSP = "/jsp";
    private static final String QUESTION_MARK = "?";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        String query = request.getQueryString();
        String requestURI = request.getRequestURI();
        if (requestURI.contains(JSP)){
            requestURI = requestURI.substring(requestURI.lastIndexOf(JSP));
            session.setAttribute(CURRENT_PAGE, requestURI);
        }
        if(query != null){
            if(servletRequest.getParameter(COMMAND) != null) {
                requestURI = CONTROLLER + query;
            }
            else {
                requestURI = request.getContextPath() + request.getServletPath() + QUESTION_MARK + query;
            }
            session.setAttribute(CURRENT_PAGE, requestURI);
        }
        filterChain.doFilter(request, servletResponse);
    }
}