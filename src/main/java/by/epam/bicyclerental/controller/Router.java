package by.epam.bicyclerental.controller;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.epam.bicyclerental.controller.Router.RouterType.REDIRECT;

public class Router {
    public enum RouterType {
        FORWARD,
        REDIRECT
    }
    private static final Logger logger = LogManager.getLogger();
    private final String pagePath;
    private final RouterType routerType;
    private static final String PATH = "/bicyclerental_war_exploded";

    public Router(String pagePath, RouterType routerType) {
        this.routerType = routerType;
        if (routerType == REDIRECT)
        {
            this.pagePath = PATH + pagePath;
        }
        else
        {
            this.pagePath = pagePath;
        }
    }

    public String getPagePath() {
        return pagePath;
    }

    public RouterType getRouterType() {
        return routerType;
    }
}
