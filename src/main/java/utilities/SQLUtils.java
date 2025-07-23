package utilities;

import commons.BaseTest;
import lombok.Getter;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SQLUtils extends BaseTest implements AutoCloseable {

    @Getter
    private static volatile SQLUtils instance;

    private final Connection connection;

    private SQLUtils(String dbUrl, String dbUsername, String dbPassword) throws SQLException {
        // Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        // Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        connection.setAutoCommit(true);
        log.info("{} OPENED.", connection);
    }

    private SQLUtils(PropertiesConfig environmentProperties) throws SQLException {
        // Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        // Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(
                environmentProperties.getPropertyValue("db.Url"),
                environmentProperties.getPropertyValue("db.Username"),
                environmentProperties.getPropertyValue("db.Password"));
        connection.setAutoCommit(true);
        log.info("{} OPENED.",  connection);
    }

    @Override
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                log.info("{} CLOSED.", connection);
            }
        } catch (SQLException e) {
            throw new RuntimeException("SQL Connection close error: " + e.getMessage(), e);
        }
    }

    public static SQLUtils getSQLConnection(String dbUrl, String dbUsername, String dbPassword) {
        if (instance == null) {
            synchronized (SQLUtils.class) {
                if (instance == null) {
                    try {
                        instance = new SQLUtils(dbUrl, dbUsername, dbPassword);
                    } catch (SQLException e) {
                        throw new RuntimeException("Database access error: " + e.getMessage(), e);
                    }
                }
            }
        }
        return instance;
    }

    public static SQLUtils getSQLConnection(PropertiesConfig environmentProperties) {
        if (instance == null) {
            synchronized (SQLUtils.class) {
                if (instance == null) {
                    try {
                        instance = new SQLUtils(environmentProperties);
                    } catch (SQLException e) {
                        throw new RuntimeException("Database access error: " + e.getMessage(), e);
                    }
                }
            }
        }
        return instance;
    }

    public List<Map<String, Object>> executeSELECTQuery(String sqlQuery, Object... params) {
        List<Map<String, Object>> resultList = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            setParams(preparedStatement, params);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                ResultSetMetaData rsmd = resultSet.getMetaData();
                int columnCount = rsmd.getColumnCount();

                while (resultSet.next()) {
                    Map<String, Object> row = new LinkedHashMap<>();
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = rsmd.getColumnLabel(i);
                        Object columnValue = resultSet.getObject(i);

                        if (columnValue instanceof java.sql.Date) {
                            columnValue = ((java.sql.Date) columnValue).toLocalDate();
                        } else if (columnValue instanceof Timestamp) {
                            columnValue = ((Timestamp) columnValue).toLocalDateTime();
                        } else if (columnValue instanceof java.sql.Time) {
                            columnValue = ((java.sql.Time) columnValue).toLocalTime();
                        }
                        row.put(columnName, columnValue);
                    }
                    resultList.add(row);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database access error occurred during SELECT query: " + e.getMessage(), e);
        }

        return resultList;
    }

    public void executeDELETEQuery(String sqlQuery, Object... params) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            setParams(preparedStatement, params);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Database access error occurred during DELETE query: " + e.getMessage(), e);
        }
    }

    private void setParams(PreparedStatement preparedStatement, Object... params) throws SQLException {
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
        }
    }

}