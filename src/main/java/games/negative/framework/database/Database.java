/*
 * MIT License
 *
 * Copyright (c) 2022 Negative
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package games.negative.framework.database;

import games.negative.framework.database.core.Statement;
import games.negative.framework.database.core.table.Collum;
import games.negative.framework.database.core.table.Table;
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
    public Database(String ip, int port, String username, String password, String databaseName) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        setIp(ip);
        setPort(port);
        setUsername(username);
        setPassword(password);
        setDatabaseName(databaseName);
    }

    /**
     * Initiate a {@code Database} using a {@code URL}
     * @param url A {@link String} which is the value of your {@code JDBC} connection string
     * @author Seailz
     */
    public Database(String url) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        this.url = url.toString();
    }

    /**
     * Initiate the connection to the database
     * @author Seailz
     */
    public void connect() throws SQLException {
            connection = url != null ? DriverManager.getConnection(
                    "jdbc:mysql://" + getIp() + ":" + getPort() + "/" + getDatabaseName(),
                    getUsername(),
                    getPassword()
            ) : DriverManager.getConnection(url);
    }

    /**
     * Creates a table within the Database
     * @param table The table you would like to create
     */
    public void createTable(Table table) throws SQLException {
        StringBuilder statement = new StringBuilder("CREATE TABLE `" + table.getName() + "` (");

        Collum last = table.getCollums().get(table.getCollums().size());
        for (Collum collum : table.getCollums()) {
            String type = collum.getType().toString();
            String name = collum.getName();

            statement.append("   `").append(name).append("` ").append(type);
            if (!last.equals(collum)) {
                statement.append(",");
            }
        }

        statement.append(");");

        new Statement(statement.toString(), connection).execute();
    }
}
