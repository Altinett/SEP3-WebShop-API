package sep3.webshop.services.data;


import org.postgresql.Driver;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DatabaseHelper<T> {
    private final String JDBC_URL, USERNAME, PASSWORD;

    public DatabaseHelper(String JDBC_URL, String USERNAME, String PASSWORD) {
        this.JDBC_URL = JDBC_URL;
        this.USERNAME = USERNAME;
        this.PASSWORD = PASSWORD;

        try {
            DriverManager.registerDriver(new Driver());
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public Connection getConnection() throws SQLException {
        if (USERNAME == null) {
            return DriverManager.getConnection(JDBC_URL);
        } else {
            return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        }
    }

    private static PreparedStatement prepare(Connection connection, String sql, Object... parameters) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sql);
        for (int i = 0; i < parameters.length; i++) {
            statement.setObject(i + 1, parameters[i]);
        }
        return statement;
    }
    private static PreparedStatement prepareWithKeys(Connection connection, String sql, Object... parameters) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        for(int i = 0; i < parameters.length; i++) {
            statement.setObject(i + 1, parameters[i]);
        }
        return statement;
    }
    public ResultSet executeQuery(Connection connection, String sql, Object... parameters) throws SQLException {
        PreparedStatement statement = prepare(connection, sql, parameters);
        return statement.executeQuery();
    }
    public int executeUpdate(String sql, Object... parameters) throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = prepare(connection, sql, parameters);
            return statement.executeUpdate();
        }
    }

    public List<Integer> executeUpdateWithGeneratedKeys(String sql, Object... parameters) throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = prepareWithKeys(connection, sql, parameters);
            statement.executeUpdate();
            LinkedList<Integer> keys = new LinkedList<>();
            ResultSet rs = statement.getGeneratedKeys();
            while(rs.next()) {
                keys.add(rs.getInt(1));
            }
            return keys;
        }
    }

    public T mapSingle(DataMapper<T> mapper, String sql, Object... parameters) throws SQLException {
        try (Connection connection = getConnection()) {
            ResultSet rs = executeQuery(connection, sql, parameters);
            if(rs.next()) {
                return mapper.create(rs);
            } else {
                return null;
            }
        }
    }

    public List<T> map(DataMapper<T> mapper, String sql, Object... parameters) throws SQLException {
        try (Connection connection = getConnection()) {
            ResultSet rs = executeQuery(connection, sql, parameters);
            LinkedList<T> all = new LinkedList<>();
            while(rs.next()) {
                all.add(mapper.create(rs));
            }
            return all;
        }
    }
}
