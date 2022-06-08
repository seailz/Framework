package games.negative.framework.database;

import lombok.Getter;
import lombok.Setter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * A way to interact with databases easier
 * @author Seailz
 */
public class Database {

    @Getter
    @Setter
    private String ip;

    @Getter
    @Setter
    private int port;

    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private String databaseName;

    private Connection connection;

    private String url = null;

    /**
     * Create a database instance
     * @param ip The ip which you would like to connect to
     * @param port The port on which the database is hosted
     * @param username The username you'd like to use
     * @param password The password you'd like to use.
     * @param databaseName The name of the database
     * @author Seailz
     */
    public Database(String ip, int port, String username, String password, String databaseName) throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");

        setIp(ip);
        setPort(port);
        setUsername(username);
        setPassword(password);
        setDatabaseName(databaseName);
    }

    public Database(String url) throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");

        this.url = url;
    }

    /**
     * Initiate the connection to the database
     * @author Seailz
     */
    public void connect() throws SQLException {
        if (url != null) {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://" + getIp() + ":" + getPort() + "/" + getDatabaseName(),
                    getUsername(),
                    getPassword()
            );
        } else
            connection = DriverManager.getConnection(url);
    }
}
