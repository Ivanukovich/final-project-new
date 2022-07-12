package by.epam.bicyclerental.controller.command;

public class PagePath {
    private static PagePath instance;
    public static final String USER_PAGE = "/jsp/user_page.jsp";
    public static final String HOME_PAGE = "/jsp/home.jsp";
    public static final String REGISTRATION_PAGE = "/jsp/registration.jsp";
    public static final String LOG_IN_PAGE = "/jsp/login.jsp";
    public static final String RENTAL_POINT_LIST_PAGE = "/jsp/rental_point_list.jsp";
    public static final String RENTAL_POINT_SELECT_PAGE = "/jsp/rental_point_select.jsp";
    public static final String ERROR_500 = "/jsp/error/error500.jsp";
    public static final String USER_LIST_PAGE = "/jsp/user_list.jsp";
    public static final String BICYCLE_LIST_PAGE = "/jsp/bicycle_list.jsp";
    public static final String USER_CREATION_PAGE = "/jsp/add_user.jsp";
    public static final String BICYCLE_SELECT_PAGE = "/jsp/bicycle_select.jsp";
    public static final String RENT_CONFIRMATION_PAGE = "/jsp/rent_confirmation.jsp";
    public static final String USER_EDIT_PAGE = "/jsp/user_edit.jsp";
    public static final String RENTAL_POINT_EDIT_PAGE = "/jsp/rental_point_edit.jsp";
    public static final String BICYCLE_EDIT_PAGE = "/jsp/bicycle_edit.jsp";
    public static final String USER_PROFILE_PAGE = "/jsp/profile.jsp";
    public static final String CHANGE_PASSWORD_PAGE = "/jsp/change_password.jsp";
    public static final String RENT_RECORD_LIST_PAGE = "/jsp/rent_record_list.jsp";

    public static PagePath getInstance() {
        if (instance == null) {
            instance = new PagePath();
        }
        return instance;
    }
}
