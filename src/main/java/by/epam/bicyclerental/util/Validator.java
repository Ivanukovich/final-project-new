package by.epam.bicyclerental.util;

public interface Validator {
    boolean validateName(String name);
    boolean validateLogin(String login);
    boolean validateEmail(String email);
    boolean validatePassword(String password);
}
