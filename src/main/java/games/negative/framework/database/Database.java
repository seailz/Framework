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
import games.negative.framework.database.core.table.Column;
import games.negative.framework.database.core.table.ColumnType;
import games.negative.framework.database.core.table.Table;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A way to interact with databases easier
 * @author Seailz - <a href="https://www.seailz.com">Website</a>
 */
@Getter
@Setter
public class Database {

    private boolean debug;

    private String ip;
    private int port;
    private String username;
    private String password;
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
    public Database(@NotNull String ip, int port, @NotNull String username, @NotNull String password, @NotNull String databaseName) throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        setIp(ip);
        setPort(port);
        setUsername(username);
        setPassword(password);
        setDatabaseName(databaseName);
    }

    /**
     * Create a database instance
     * @param ip The ip which you would like to connect to
     * @param port The port on which the database is hosted
     * @param username The username you'd like to use
     * @param password The password you'd like to use.
     * @param databaseName The name of the database
     * @param debug Whether you'd like to debug the database
     * @author Seailz
     */
    public Database(@NotNull String ip, int port, @NotNull String username, @NotNull String password, @NotNull String databaseName, @NotNull boolean debug) throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        setIp(ip);
        setPort(port);
        setUsername(username);
        setPassword(password);
        setDatabaseName(databaseName);
        setDebug(debug);

        if (debug)
            System.out.println("[Database] Debugging enabled");
    }

    /**
     * Initiate the connection to the database
     * @author Seailz
     */
    public void connect() throws SQLException {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://" + getIp() + ":" + getPort() + "/" + getDatabaseName(),
                    getUsername(),
                    getPassword()
            );

            if (debug)
                System.out.println("[Database] Connected to database");
    }

    /**
     * Disconnect from the database
     * @throws SQLException If the connection is already closed
     */
    public void disconnect() throws SQLException {
        connection.close();
        if (debug)
            System.out.println("[Database] Disconnected from database");
    }

    /**
     * Creates a table within the Database
     * @param table The table you would like to create
     * @author Seailz
     */
    public void createTable(@NotNull Table table) throws SQLException {
        StringBuilder statement = new StringBuilder("CREATE TABLE `" + table.getName() + "` (\n");

        Column last = table.getColumns().get(table.getColumns().size() - 1);
        Column first = table.getColumns().stream().findFirst().get();
        for (Column column : table.getColumns()) {
            String type = column.getType().toString();
            String name = column.getName();

            if (first == column)
                statement.append("\t`").append(name).append("` ").append(type);
            else
                statement.append("\n\t`").append(name).append("` ").append(type);


            statement.append("(").append(column.getLength()).append(")");

            if (!column.isAllowNull())
                statement.append(" NOT NULL");


            if (!last.equals(column))
                statement.append(",");

        }

        if (table.getPrimaryKey() != null)
            statement.append(",\n\tPRIMARY KEY (`").append(table.getPrimaryKey()).append("`)");

        statement.append("\n);");

        if (debug)
            System.out.println("[Database] Creating table: " + statement.toString());

        new Statement(statement.toString(), connection).execute();
    }

    /**
     * Get something from the database
     * <p></p>
     * <p>For example, if you wanted to get the details about a player,</p>
     * <p>the key parameter would be "name" or whatever it is within your table</p>
     * <p>and the value parameter would be the player's name of whom you wish to get the details of.</p>
     * <p></p>
     * <p>The "column" parameter would be the specific detail you'd like to get. For example, </p>
     * <p>if my table contained a "age" column, and I wanted to get the player's age,</p>
     * <p>I'd set the column parameter to "age"</p>
     * <p>
     *
     * @param table the table you'd like to pull from
     * @param key The key you'd like to check
     * @param value The value that you'd like to check
     * @param column The column you'd like to get
     * @return An object
     * @throws SQLException if there is an error retrieving the request value
     * @author Seailz
     */
    public Object get(@NotNull String table, @NotNull String key, @NotNull String value, @NotNull String column) throws SQLException {
        String statement = "SELECT * FROM '" + table + "'";
        ResultSet set = new Statement(statement, connection).executeWithResults();

        while (set.next()) {
            if (set.getObject(key).equals(value))
                return set.getObject(column);
        }
        if (debug)
            System.out.println("[Database] Getting value: " + statement);
        return null;
    }

    /**
     * Check if a table exists
     * @param tableName The table you'd like to check
     * @return A boolean if the table exists or not
     * @throws SQLException If there is an error
     */
    public boolean tableExists(@NotNull String tableName) throws SQLException {
        DatabaseMetaData meta = connection.getMetaData();
        ResultSet resultSet = meta.getTables(null, null, tableName, new String[] {"TABLE"});
        if (debug)
            System.out.println("[Database] Checking if table exists: " + tableName);
        return resultSet.next();
    }

    /**
     * Insert into a database
     * @param table The table you'd like to insert to
     * @param values A hashmap of keys, and values
     * @throws SQLException if there is an error
     */
    public void insert(@NotNull String table, @NotNull HashMap<String, String> values) throws SQLException {
        StringBuilder statement = new StringBuilder("insert into '" + table + "' (");

        ArrayList<String> keysArray = new ArrayList<>(values.keySet());
        String lastKey = keysArray.get(keysArray.size() - 1);
        for (String key : values.keySet()) {
            if (!key.equals(lastKey))
                statement.append("'").append(key).append("', ");
            else
                statement.append("'").append(key).append(")");
        }

        statement.append(" values (");

        ArrayList<String> valuesArray = new ArrayList<>(values.values());
        String lastValue = valuesArray.get(valuesArray.size() - 1);
        for (String value : values.values()) {
            if (!value.equals(lastValue))
                statement.append("?, ");
            else
                statement.append("?)");
        }

        if (debug)
            System.out.println(statement);

        PreparedStatement prepStatement = connection.prepareStatement(statement.toString());
        int i = 0;

        for (String value : values.values()) {
            i++;
            prepStatement.setObject(i, value);
        }

        if (debug)
            System.out.println("[Database] Inserting into table: " + statement.toString());
        prepStatement.execute();
    }

    /**
     * Delete a row rom the database
     * @param table The table you'd like to edit
     * @param key The key, basically the identifier
     * @param value The value, such as the player's name
     */
    public void delete(@NotNull String table, @NotNull String key, @NotNull String value) throws SQLException {
        String statement = "DELETE FROM '" + table + "' WHERE '" + key + "'='" + value + "'";
        new Statement(statement, connection).execute();
        if (debug)
            System.out.println("[Database] Deleting from table: " + statement);
    }

    /**
     * Check if a row exists
     * @param table The table you'd like to check
     * @param key The key
     * @param value The value
     * @return whether that row exists
     */
    public boolean rowExists(@NotNull String table, @NotNull String key, @NotNull String value) throws SQLException {
        String statement = "SELECT * FROM `" + table + "` WHERE '" + key + "'='" + value + "'";
        if (debug)
            System.out.println("[Database] Checking if row exists: " + statement);
        return new Statement(statement, connection).executeWithResults().next();
    }

    /**
     * Replace a current row with a new one
     * @param table The table in which the row is located
     * @param key The key you would like to check
     * @param value the value of that key
     * @param values the values of the new row you'd like to insert
     * @throws SQLException If there's an error communicating with the database
     */
    public void replace(@NotNull String table, @NotNull String key, @NotNull String value, @NotNull HashMap<String, String> values) throws SQLException {
        if (!rowExists(table, key, value)) return; // Trying to prevent as many errors as possible :/

        if (debug)
            System.out.println("[Database] Replacing row: " + table + "." + key + "=" + value);

        delete(table, key, value);
        insert(table, values);
    }

    /**
     * Delete a table
     * @param name The name of the table you'd like to delete
     * @throws SQLException if there is an error communicating with the database
     */
    public void deleteTable(@NotNull String name) throws SQLException {
        if (!tableExists(name)) return;
        if (debug)
            System.out.println("[Database] Deleting table: " + name);
        new Statement("DROP TABLE " + name + ";", connection).execute();
    }

    /**
     * Update a row in a table
     * @param table The table you'd like to update
     * @param key The key you'd like to check
     * @param value The value you'd like to check
     * @param column The column you'd like to update
     * @param newColumn The new value you'd like to insert
     * @throws SQLException if there is an error communicating with the database
     */
    public void update(@NotNull String table, @NotNull String key, @NotNull String value, @NotNull String column, @NotNull String newColumn) throws SQLException {
        String statement = "UPDATE `" + table + "` SET `" + column + "`=`" + newColumn + "` WHERE `" + key + "`='" + value + "'";
        if (debug)
            System.out.println("[Database] Updating row: " + statement);
        new Statement(statement, connection).execute();
    }


    /**
     * Update a table in the database
     * @param table The table you'd like to update
     * @param column The column you'd like to update
     * @param type The type of the column
     * @throws SQLException if there is an error communicating with the database
     */
    public void addColumnToTable(String table, String column, String type) throws SQLException {
        String statement = "ALTER TABLE `" + table + "` ADD `" + column + "` " + type + ";";
        if (debug)
            System.out.println("[Database] Altering table: " + statement);
        new Statement(statement, connection).execute();
    }

    /**
     * Remove a column from a table
     * @param table The table you'd like to remove a column from
     * @param column The column you'd like to remove
     * @throws SQLException if there is an error communicating with the database
     */
    public void removeColumnFromTable(String table, String column) throws SQLException {
        String statement = "ALTER TABLE `" + table + "` DROP COLUMN `" + column + "`;";
        if (debug)
            System.out.println("[Database] Altering table: " + statement);
        new Statement(statement, connection).execute();
    }
}
