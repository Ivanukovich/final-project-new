package by.epam.bicyclerental.controller.filter.permission;

import java.util.Set;

import static by.epam.bicyclerental.controller.command.PagePath.*;

public enum RolePages {
    ADMINISTRATOR(Set.of(START_PAGE,
            HOME_PAGE,
            LOG_IN_PAGE,
            REGISTRATION_PAGE,
            ERROR_500,
            USER_PAGE,
            PROFILE_PAGE,
            CHANGE_PASSWORD_PAGE,
            USER_LIST_PAGE,
            BICYCLE_LIST_PAGE,
            RENTAL_POINT_LIST_PAGE,
            USER_EDIT_PAGE,
            BICYCLE_EDIT_PAGE,
            RENTAL_POINT_EDIT_PAGE,
            USER_CREATION_PAGE,
            BICYCLE_CREATION_PAGE,
            RENTAL_POINT_CREATION_PAGE,
            DATABASE_CONTROL_PAGE)),

    INACTIVE_UNBLOCKED(Set.of(RENTAL_POINT_SELECT_PAGE,
            BICYCLE_SELECT_PAGE,
            RENT_CONFIRMATION_PAGE)),

    USER(Set.of(START_PAGE,
            HOME_PAGE,
            LOG_IN_PAGE,
            REGISTRATION_PAGE,
            ERROR_500,
            USER_PAGE,
            PROFILE_PAGE,
            CHANGE_PASSWORD_PAGE)),

    GUEST(Set.of(START_PAGE,
            HOME_PAGE,
            LOG_IN_PAGE,
            REGISTRATION_PAGE,
            ERROR_500));

    private final Set<String> rolePages;

    RolePages(Set<String> userPages){
        this.rolePages = userPages;
    }
    public Set<String> getUserPages(){
        return rolePages;
    }
}
