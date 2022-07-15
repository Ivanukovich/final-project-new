package by.epam.bicyclerental.controller.command;

public class Parameter {
    private static Parameter instance;

    public static final String COMMAND = "command";

    public static final String USER = "user";
    public static final String USER_ID = "user_id";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";
    public static final String EMAIL = "email";
    public static final String USER_ROLE = "user_role";
    public static final String USER_STATUS = "user_status";
    public static final String NEW_PASSWORD = "new_password";
    public static final String USER_BLOCKED = "is_blocked";
    public static final String USER_LOGINS = "user_logins";


    public static final String LOGIN_ALREADY_EXISTS = "login_already_exists";
    public static final String EMAIL_ALREADY_EXISTS = "email_already_exists";
    public static final String INVALID_FIRST_NAME = "invalid_first_name";
    public static final String INVALID_LAST_NAME = "invalid_last_name";
    public static final String INVALID_LOGIN = "invalid_login";
    public static final String INVALID_EMAIL = "invalid_email";
    public static final String INVALID_PASSWORD = "invalid_password";
    public static final String NEW_PASSWORD_EQUALS_OLD = "new_password_equals_old";
    public static final String WRONG_PASSWORD = "wrong_password";
    public static final String INCORRECT_LOGIN_OR_PASSWORD = "incorrect_login_or_password";

    public static final String EMAIL_ALREADY_EXISTS_MESSAGE = "error.email_already_exists";
    public static final String LOGIN_ALREADY_EXISTS_MESSAGE = "error.login_already_exists";
    public static final String INVALID_FIRST_NAME_MESSAGE = "error.invalid_first_name";
    public static final String INVALID_LAST_NAME_MESSAGE = "error.invalid_last_name";
    public static final String INVALID_LOGIN_MESSAGE = "error.invalid_login";
    public static final String INVALID_EMAIL_MESSAGE = "error.invalid_email";
    public static final String INVALID_PASSWORD_MESSAGE = "error.invalid_password";
    public static final String NEW_PASSWORD_EQUALS_OLD_MESSAGE = "error.new_password_equals_old";
    public static final String WRONG_PASSWORD_MESSAGE = "error.wrong_password";
    public static final String INCORRECT_LOGIN_OR_PASSWORD_MESSAGE = "error.incorrect_login_or_password";

    public static final String SELECTED_BICYCLE = "bicycle";
    public static final String BICYCLE_ID = "bicycle_id";
    public static final String BICYCLE_MODEL = "model";
    public static final String BICYCLE_STATUS = "bicycle_status";
    public static final String BICYCLE_LIST = "bicycle_list";
    public static final String BICYCLE = "bicycle";
    public static final String BICYCLE_MODELS = "bicycle_models";
    public static final String RENT_RECORD_LIST = "rent_record_list";
    public static final String RENTAL_POINT_LOCATIONS = "rental_point_locations";

    public static final String ERROR_MESSAGE = "error_message";
    public static final String LOCALE = "locale";
    public static final String RUSSIAN_LOCALE = "ru_RU";
    public static final String ENGLISH_LOCALE = "en_US";

    public static final String LOCATION = "location";
    public static final String RENT_LIST = "rent_list";
    public static final String RENT_RECORD_ID = "rent_record_id";

    public static final String RENTAL_POINT = "rental_point";
    public static final String RENTAL_POINT_ID = "rental_point_id";
    public static final String RENTAL_POINT_LOCATION = "location";
    public static final String RENTAL_POINT_LIST = "rental_point_list";
    public static final String RENTAL_POINT_BICYCLES_LIST = "rental_point_bicycle_list";
    public static final String INACTIVE_BICYCLES_LIST = "inactive_bicycle_list";

    public static final String USER_LIST = "user_list";

    public static final String CURRENT_PAGE = "current_page";

    public static Parameter getInstance() {
        if (instance == null) {
            instance = new Parameter();
        }
        return instance;
    }
}
