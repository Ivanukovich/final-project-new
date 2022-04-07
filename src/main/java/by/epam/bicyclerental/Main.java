package by.epam.bicyclerental;

import by.epam.bicyclerental.exception.DaoException;
import by.epam.bicyclerental.model.dao.impl.UserDaoImpl;
import by.epam.bicyclerental.model.entity.User;
import by.epam.bicyclerental.model.entity.UserStatus;
import by.epam.bicyclerental.model.pool.CustomConnectionPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException, DaoException {
        //CustomConnectionPool.getInstance();
        //UserDaoImpl s = UserDaoImpl.getInstance();
        //s.findAllActiveUsers();
        //Class.forName("com.mysql.cj.jdbc.Driver");
        //Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bicycles", "root", "mam080171");
    }
}
