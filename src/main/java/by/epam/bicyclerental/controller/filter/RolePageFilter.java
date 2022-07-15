package by.epam.bicyclerental.controller.filter;

import by.epam.bicyclerental.controller.filter.permission.RolePages;
import by.epam.bicyclerental.model.entity.UserRole;
import by.epam.bicyclerental.model.entity.UserStatus;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Set;

import static by.epam.bicyclerental.controller.command.PagePath.HOME_PAGE;
import static by.epam.bicyclerental.controller.command.Parameter.*;

@WebFilter(urlPatterns = {"/*"})
public class RolePageFilter implements Filter {
    private static final Logger logger = LogManager.getLogger();

    private static final String PATH = "/bicyclerental_war_exploded";
    private static final String JSP = "/jsp";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws ServletException, IOException, IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        String requestURI = request.getServletPath();
        if (requestURI.contains(JSP)) {
            UserRole role = (UserRole) session.getAttribute(USER_ROLE);
            Set<String> pages;
            boolean access;
            if (role != null) {
                UserStatus status = (UserStatus) session.getAttribute(USER_STATUS);
                boolean blocked = (boolean) session.getAttribute(USER_BLOCKED);
                switch (role) {
                    case ADMINISTRATOR:
                        pages = RolePages.ADMINISTRATOR.getUserPages();
                        access = pages.stream().anyMatch(requestURI::contains);
                        break;
                    case USER:
                        pages = RolePages.USER.getUserPages();
                        access = pages.stream().anyMatch(requestURI::contains);
                        break;
                    default:
                        pages = RolePages.GUEST.getUserPages();
                        access = pages.stream().anyMatch(requestURI::contains);
                        break;
                }
                if (!access && !blocked && status == UserStatus.INACTIVE) {
                    pages = RolePages.INACTIVE_UNBLOCKED.getUserPages();
                    access = pages.stream().anyMatch(requestURI::contains);
                }
            } else {
                pages = RolePages.GUEST.getUserPages();
                access = pages.stream().anyMatch(requestURI::contains);
            }
            if (!access) {
                response.sendRedirect(PATH + HOME_PAGE);
                return;
            }
        }
        chain.doFilter(servletRequest, servletResponse);
    }
}
