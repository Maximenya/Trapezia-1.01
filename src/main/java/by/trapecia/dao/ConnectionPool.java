package by.trapecia.dao;

import by.trapecia.Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class represent container for connections.
 */
public final class ConnectionPool {

    private static Logger log = Logger.getLogger(Main.class.getName());

    /**
     * Free connection.
     *
     * Using to store free connection.
     */
    private BlockingQueue<Connection> freeConnection;

    /**
     * Busy connection.
     *
     * Using to store all connection.
     */
    private List<Connection> allConnection;


    private static ConnectionPool instance = new ConnectionPool("jdbc:mysql://localhost:3306/trapecia?characterEncoding=UTF-8","com.mysql.jdbc.Driver", "root", "775678896Deo", 5);

    public static ConnectionPool getInstance(){
        return instance;
    }
    /**
     * After creating pool get MAX_CONNECTION_COUNT connections from jdbc and
     * put in freeConnection;
     *
     * @param driver
     * @param url
     * @param user
     * @param password
     * @throws Exception
     */
    private ConnectionPool(String url, String driver, String user,
                          String password, Integer maxConnectionCount) {
        freeConnection = new ArrayBlockingQueue<>(maxConnectionCount);
        allConnection = new ArrayList<>(maxConnectionCount);
        try {
            Class.forName(driver);
            for (int i = 0; i < maxConnectionCount; i++) {
                Connection connection = DriverManager.getConnection(url, user, password);
                freeConnection.add(connection);
                allConnection.add(connection);
            }
        } catch (ClassNotFoundException e) {
            log.log(Level.SEVERE, "Exception: ", e);
            //throw new Exception("Cant create pool, class not found", e);
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Exception: ", e);
//            throw new Exception("Cant create pool, sql error: "
//                    + e.getMessage(), e);
        }
    }

    /**
     * Get connection.
     *
     * When somebody try get connection if freeConnection is empty he wait while
     * in freeConnection appear a connection. And before return connection to
     * somebody pool put connection to busy connections.
     *
     * @return Connection
     * @throws Exception
     */
    public Connection getConnection() throws Exception {
        Connection connection;
        try {
            connection = freeConnection.take();
            if (null == connection) {
                throw new Exception("null connection in poll");
            }

            try {
                if (connection.isClosed()) {
                    throw new Exception("closed connection in pool");
                }
            } catch (SQLException e) {
                System.out.println("Error when try check closeble connection in pool");
            }
        } catch (InterruptedException e) {
            log.log(Level.SEVERE, "Exception: ", e);
            throw new Exception("Cant take pool connection", e);
        }
        return connection;
    }

    /**
     * Return connection.
     *
     * Return connection back to pool. Throws Exception if connection is
     * null, closed, getting not from this pool or connection cannot be checked
     * for closable(SQLException)
     *
     * @param connection
     * @throws Exception
     */

    public void returnConnection(Connection connection)
            throws Exception {

        if (null == connection) {
            throw new Exception("Try return null connection");
        }

        try {
            if (connection.isClosed()) {
                throw new Exception("Try return closed connection");
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Exception: ", e);
           System.out.println(
                   "Error when try check closeble connection in pool");
        }
        freeConnection.add(connection);
    }

    /**
     * Destroy.
     *
     * Close all connection.
     *
     * @throws SQLException
     */
    public void destroy() throws SQLException {
        for (Connection connection : allConnection) {
            connection.close();
        }
    }

}