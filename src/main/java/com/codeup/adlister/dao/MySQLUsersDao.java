package com.codeup.adlister.dao;
import com.codeup.adlister.models.User;
import com.mysql.cj.jdbc.Driver;

import java.sql.*;

/**
 * Created by RyanHarper on 10/23/17.
 */
public class MySQLUsersDao implements Users {

    private Connection connection;

    public MySQLUsersDao() {
        try {
            DriverManager.registerDriver(new Driver());
            connection = DriverManager.getConnection(
                    Config.url,
                    Config.username,
                    Config.password
            );
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database!", e);
        }
    }



    @Override
    public User findByUsername(String username) {
        String findQuery = "SELECT * FROM users WHERE username = ?";

        try {
            //create a prepared statement and pass the sql:
            PreparedStatement statement = connection.prepareStatement(findQuery);
            //find the parameters:
            statement.setString(1, username);
            //now execute the query and it will return a ResultSet:
            ResultSet resultSet = statement.executeQuery();
            //check if user isn't there and create a new user
            if (resultSet.next()) {
                return new User(
                        resultSet.getLong("id"),
                        resultSet.getString("username"),
                        resultSet.getString("email"),
                        resultSet.getString("password")
                );
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding user!");
        }
    }

    @Override
    public Long insert(User user) {
        String insertQuery = "INSERT INTO users (username, email, password) " +
                "VALUES (?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(insertQuery,
                    Statement.RETURN_GENERATED_KEYS
            );
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());

            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            return rs.getLong(1);

        } catch (SQLException e) {
            throw new RuntimeException("Error inserting a user!");
        }
    }
}
