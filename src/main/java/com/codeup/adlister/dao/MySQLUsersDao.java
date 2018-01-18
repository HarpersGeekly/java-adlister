package com.codeup.adlister.dao;
import com.codeup.adlister.Config;
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
    public Long insert(User user) {
        //RECIPE FOR THE INSERT

        //write the sql for the insert
        String sql = "INSERT INTO users (username, email, password) " +
                "VALUES (?, ?, ?)";

        try {
            //create a prepared statement with the insert, request the generated keys
            PreparedStatement statement = connection.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS
            );
            //bind the parameters with their values '?' from the sql query
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());

            //execute the query
            statement.executeUpdate();

            //return the generated key value
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            return rs.getLong(1);

        } catch (SQLException e) {
            throw new RuntimeException("Error inserting a user!", e);
        }
    }

    @Override
    public User findById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getLong("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password")

                );
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding user by id!", e);
        }
    }

    @Override
    public User findByUsername(String username) {
        //RECIPE FOR ANY FINDER:

        //write the sql query:
        String sql = "SELECT * FROM users WHERE username = ? LIMIT 1";

        try {
            //create a prepared statement and pass the sql:
            PreparedStatement statement = connection.prepareStatement(sql);
            //bind the parameters:
            statement.setString(1, username);
            //now execute the query and it will return a ResultSet:
            ResultSet resultSet = statement.executeQuery();
            //if there's at least one row, build a User
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
            throw new RuntimeException("Error finding user by username!", e);
        }
    }

    @Override
    public void updateUser(User user) {

        try {
            PreparedStatement statement = connection.prepareStatement
                    ("UPDATE users SET username = ?, email = ? WHERE id = ?"
                    );
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
//            statement.setString(3, user.getPassword());
            statement.setLong(3, user.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error updating user", e);
        }
    }

    @Override
    public void updatePassword(User user) {

        try {
            PreparedStatement statement = connection.prepareStatement
                    ("UPDATE users SET password = ? WHERE id = ?"
                    );
            statement.setString(1, user.getPassword());
            statement.setLong(2, user.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error updating password", e);
        }
    }

    @Override
    public void deleteUser(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM users WHERE id= ?"
            );

            statement.setLong(1, user.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error DELETING user", e);
        }
    }

}
