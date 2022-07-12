package by.epam.bicyclerental.controller.filter;

import by.epam.bicyclerental.controller.command.Parameter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import java.io.IOException;

@WebFilter(urlPatterns = {"/*"})
public class LocaleFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request= (HttpServletRequest) servletRequest;
        if(request.getSession().getAttribute(Parameter.LOCALE) == null){
            request.getSession().setAttribute(Parameter.LOCALE, Parameter.RUSSIAN_LOCALE);
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }
}