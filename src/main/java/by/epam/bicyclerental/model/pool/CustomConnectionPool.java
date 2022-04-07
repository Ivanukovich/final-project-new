package by.epam.bicyclerental.model.pool;

import by.epam.bicyclerental.exception.CustomPoolException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class CustomConnectionPool {
    private static CustomConnectionPool instance;
    private static final ReentrantLock instanceLock = new ReentrantLock();

    private static final Logger logger = LogManager.getLogger();
    private final BlockingQueue<ProxyConnection> freeConnections;
    private final BlockingQueue<ProxyConnection> givenAwayConnections;

    private static final int POOL_SIZE;
    private static final String URL;
    private static final String USERNAMR;
    private static final String PASSWORD;
    private static final String DRIVER;

    static {
        ResourceBundle bundle = ResourceBundle.getBundle("sqldata.database");
        URL = bundle.getString("url");
        USERNAMR = bundle.getString("username");
        PASSWORD = bundle.getString("password");
        POOL_SIZE = Integer.parseInt(bundle.getString("poolsize"));
        DRIVER = bundle.getString("driver");
    }

    private CustomConnectionPool(){
        freeConnections = new LinkedBlockingDeque<>(POOL_SIZE);
        givenAwayConnections = new LinkedBlockingDeque<>();
        try {
            Class.forName(DRIVER);
            for (int i = 0; i < POOL_SIZE; ++i) {
                freeConnections.add(new ProxyConnection(DriverManager.getConnection(URL, USERNAMR, PASSWORD)));
            }
        } catch (ClassNotFoundException | SQLException exception) {
            logger.fatal(exception.getMessage());
            throw new RuntimeException(exception.getMessage());
        }
    }

    public static CustomConnectionPool getInstance() {
        if (instance == null) {
            instanceLock.lock();
            if (instance == null) {
                instance = new CustomConnectionPool();
            }
        }
        return instance;
    }

    public Connection getConnection(){
        ProxyConnection connection = null;
        try{
            connection = freeConnections.take();
            givenAwayConnections.offer(connection);
        }catch (InterruptedException e){
            logger.log(Level.ERROR,"The thread was interrupted!" + e.getMessage());
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    public void releaseConnection(Connection connection){
        try {
            if(connection.getClass() != ProxyConnection.class){
                throw new CustomPoolException("Illegal connection!");
            }
            ProxyConnection proxyConnection = (ProxyConnection) connection;
            givenAwayConnections.remove(proxyConnection);
            freeConnections.put(proxyConnection);
        }catch (CustomPoolException | InterruptedException e){
            logger.log(Level.ERROR,e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    public void destroyPool(){
        for(int i = 0; i < POOL_SIZE; i++){
            try {
                freeConnections.take().realClose();
            } catch (InterruptedException e) {
                logger.log(Level.ERROR,"The thread was interrupted!" + e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
        deregisterDrivers();
    }
    private void deregisterDrivers(){
        DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                logger.log(Level.ERROR, "The driver was not removed");
            }
        });
    }
}
