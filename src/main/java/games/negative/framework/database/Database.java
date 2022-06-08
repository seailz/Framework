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
@Getter
public class Database {

    @Setter
    private String ip;

    @Setter
    private int port;

    @Setter
    private String username;

    @Setter
    private String password;

    @Setter
    private String databaseName;

    private Connection connection;

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

    /**
     * Initiate the connection to the database
     * @author Seailz
     */
    public void connect() throws SQLException {
        // TODO: Connect
        connection = DriverManager.getConnection(
                "jdbc:mysql://" + getIp() + ":" + getPort() + "/feedback?"
                        + "user=" + getUsername() + "&password=" + getPassword()
        );
    }
}
