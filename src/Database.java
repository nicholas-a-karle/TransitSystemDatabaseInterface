import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Database implements AutoCloseable {

    // JDBC Connection
    protected Connection connection;

    public Database(String databaseName, String jdbc_url, String username, String password) throws Exception {
        System.out.println("Starting database connection");
        try {
            System.out.println("Establishing a connection to MySQL server");
            // Establishing a connection to MySQL server
            connection = DriverManager.getConnection(jdbc_url, username, password);
            System.out.println("Connected");
            if (connection == null) {
                throw new Exception(String.format("Connection null: %s",jdbc_url));
            } else {
                System.out.println("Connected to the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        createDatabase(databaseName);
        selectDatabase(databaseName);
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Connection to the database closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws Exception {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            System.out.println("Connection to the database closed.");
        }
    }

    protected void createDatabase(String databaseName) {
        // SQL statement to create the database
        String sqlCreateDatabase = "CREATE DATABASE IF NOT EXISTS " + databaseName;

        // Execute SQL statement
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlCreateDatabase);
            System.out.println("Database created successfully: " + databaseName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void selectDatabase(String databaseName) {
        try (Statement statement = connection.createStatement()) {
            String sql = "USE " + databaseName;
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void insertData(String tableName, String[] values) {
        // Construct the SQL INSERT statement
        StringBuilder sqlInsert = new StringBuilder("INSERT INTO ");
        sqlInsert.append(tableName).append(" VALUES (");
        for (int i = 0; i < values.length; i++) {
            sqlInsert.append("?");
            if (i != values.length - 1) {
                sqlInsert.append(", ");
            }
        }
        sqlInsert.append(")");
    
        // Execute the SQL INSERT statement
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert.toString())) {
            // Set the values for the prepared statement
            for (int i = 0; i < values.length; i++) {
                preparedStatement.setString(i + 1, values[i]);
            }
            // Execute the INSERT statement
            int rowsInserted = preparedStatement.executeUpdate();
            System.out.println(rowsInserted + " row(s) inserted into " + tableName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    protected void createTable(String tableName, String[] columns) {
        String fullSqlStatement = "CREATE TABLE IF NOT EXISTS " + tableName + " (";
        // add in all the values of columns into it
        for (int i = 0; i < columns.length; i++) {
            fullSqlStatement += columns[i];
            if (i != columns.length - 1) {
                fullSqlStatement += ", ";
            }
        }
        fullSqlStatement += ")";

        // Execute SQL statement
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(fullSqlStatement);
            System.out.println("Table created successfully: " + tableName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
