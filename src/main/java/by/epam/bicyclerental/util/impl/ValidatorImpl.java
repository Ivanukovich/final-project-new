package by.epam.bicyclerental.util.impl;

import by.epam.bicyclerental.util.Validator;

public class ValidatorImpl implements Validator {
    private static final String NAME_PATTERN = "^([A-Z][a-z]{2,19})|([А-Я][а-я]{2,19})$";
    private static final String LOGIN_PATTERN = "^[A-Za-z0-9_]{3,20}$";
    private static final String EMAIL_PATTERN = "^[A-Za-z0-9_.]{3,20}@[a-z]{4,8}\\.[a-z]{2,4}$";
    private static final String PASSWORD_PATTERN = "^[A-Za-zА-Яа-я0-9_]{8,16}$";

    private static ValidatorImpl instance;

    public static ValidatorImpl getInstance() {
        if (instance == null) {
            instance = new ValidatorImpl();
        }
        return instance;
    }

    @Override
    public boolean validateName(String name) {
        return name.matches(NAME_PATTERN);
    }

    @Override
    public boolean validateLogin(String login) {
        return login.matches(LOGIN_PATTERN);
    }

    @Override
    public boolean validateEmail(String email) {
        return email.matches(EMAIL_PATTERN);
    }

    @Override
    public boolean validatePassword(String password) {
        return password.matches(PASSWORD_PATTERN);
    }
}
